(function () {

  let btnStatus = "UNDEFINED"; // "UNDEFINED" "CONNECTING" "OPEN" "CLOSING" "CLOSED"

  const btnControl = document.getElementById("btn_control");

  const recorder = new RecorderManager("../../dist");
  recorder.onStart = () => {
    changeBtnStatus("OPEN");
  }
  let iatWS;
  let resultText = "";
  let resultTextTemp = "";
  let countdownInterval;

  /**
   * 获取websocket url
   * 该接口需要后端提供，这里为了方便前端处理
   */
  function getWebSocketUrl() {
    // 请求地址根据语种不同变化
    var url = "wss://rtasr.xfyun.cn/v1/ws";
    var appId = APPID;
    var secretKey = API_KEY;
    var ts = Math.floor(new Date().getTime() / 1000);
    var signa = hex_md5(appId + ts);
    var signatureSha = CryptoJSNew.HmacSHA1(signa, secretKey);
    var signature = CryptoJS.enc.Base64.stringify(signatureSha);
    signature = encodeURIComponent(signature);
    return `${url}?appid=${appId}&ts=${ts}&signa=${signature}`;
  }

  function changeBtnStatus(status) {
    btnStatus = status;
    if (status === "CONNECTING") {
      btnControl.innerText = "建立连接中";
      document.getElementById("result").innerText = "";
      resultText = "";
      resultTextTemp = "";
    } else if (status === "OPEN") {
      btnControl.innerText = "录音中";
    } else if (status === "CLOSING") {
      btnControl.innerText = "关闭连接中";
    } else if (status === "CLOSED") {
      btnControl.innerText = "开始录音";
    }
  }

  function renderResult(resultData) {
    let jsonData = JSON.parse(resultData);
    if (jsonData.action == "started") {
      // 握手成功
      console.log("握手成功");
    } else if (jsonData.action == "result") {
      const data = JSON.parse(jsonData.data)
      console.log(data)
      // 转写结果
      let resultTextTemp = ""
      data.cn.st.rt.forEach((j) => {
        j.ws.forEach((k) => {
          k.cw.forEach((l) => {
            resultTextTemp += l.w;
          });
        });
      });
      if (data.cn.st.type == 0) {
        // 【最终】识别结果：
        resultText += resultTextTemp;
        resultTextTemp = ""
      }

      document.getElementById("result").innerText = resultText + resultTextTemp
    } else if (jsonData.action == "error") {
      // 连接发生错误
      console.log("出错了:", jsonData);
    }
  }

  function connectWebSocket() {
    const websocketUrl = getWebSocketUrl();
    if ("WebSocket" in window) {
      iatWS = new WebSocket(websocketUrl);
    } else if ("MozWebSocket" in window) {
      iatWS = new MozWebSocket(websocketUrl);
    } else {
      alert("浏览器不支持WebSocket");
      return;
    }
    changeBtnStatus("CONNECTING");
    iatWS.onopen = (e) => {
      // 开始录音
      recorder.start({
        sampleRate: 16000,
        frameSize: 1280,
      });
    };
    iatWS.onmessage = (e) => {
      renderResult(e.data);
    };
    iatWS.onerror = (e) => {
      console.error(e);
      recorder.stop();
      changeBtnStatus("CLOSED");
    };
    iatWS.onclose = (e) => {
      recorder.stop();
      changeBtnStatus("CLOSED");
    };
  }

  recorder.onFrameRecorded = ({ isLastFrame, frameBuffer }) => {
    if (iatWS.readyState === iatWS.OPEN) {
      iatWS.send(new Int8Array(frameBuffer));
      if (isLastFrame) {
        iatWS.send('{"end": true}');
        changeBtnStatus("CLOSING");
      }
    }
  };
  recorder.onStop = () => {
    clearInterval(countdownInterval);
  };

  btnControl.onclick = function () {
    if (btnStatus === "UNDEFINED" || btnStatus === "CLOSED") {
      connectWebSocket();
    } else if (btnStatus === "CONNECTING" || btnStatus === "OPEN") {
      // 结束录音
      recorder.stop();
    }
  };
})();
