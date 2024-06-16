/*
Date: 5/24/2024
Author: lu0qlng
Description: 录音开始、结束、点击后跳转
*/


function sleep(time){
    const timeStamp = new Date().getTime();
    const endTime = timeStamp + time;
    while(true){
        if (new Date().getTime() > endTime){
            return;
        }
    }
}

const content = document.getElementById("popup_input");
content.value = "请说出你想要的书";
// const shell = document.getElementById("popup_input_shell");
// console.log(shell)

const btn = document.getElementById("voiceButton");
// console.log(btn)
const recognition = new webkitSpeechRecognition();

recognition.continuous = true;
recognition.lang = 'zh-CN'
recognition.interimResults = true

recognition.onresult = function (event) {
    let result = ''
    for (let i = 0; i <= event.resultIndex; i++) {
        result += event.results[i][0].transcript;
        for (const stringsKey in ["。", ",", "!", "?", "？", "！", "，", "."]) {
            result = result.replaceAll(stringsKey, "")
        }
    }
    content.value = result
}

recognition.onreadystatechange = function (){
    // console.log(recognition.readyState)
}


function voiceDictation() {
    if (btn.dataset.pressed === 'true') {
        // console.log("stop")
        recognition.stop();
        btn.dataset.pressed = 'false';
    } else {
        // console.log("start")
        recognition.start();
        btn.dataset.pressed = 'true';
    }
}


function showPopup(){
    const overlay = document.getElementById("overlay");
    overlay.style.display = "block";
    voiceDictation()
}
function hidePopup(){
    const overlay = document.getElementById("overlay");
    overlay.style.display = "none";
    content.value = "请说出你想要的书";
    voiceDictation();
}
function searchKid(){
    let ask = content.value;
    window.location.href = "result-parents.html?ask="+ask;
}

let sqlString = getRandomBooks(18);
const params = new URLSearchParams({
    "sql": sqlString
});

let req = new XMLHttpRequest();
req.open("POST", "/PBRS/searchAny");
req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

req.onreadystatechange = () => {
    if (req.readyState === 4 && req.status === 200) {
        let bookJson = JSON.parse(req.responseText)
        let len = Object.keys(bookJson).length

        let book;
        let container;
        for (let i = 0; i < len; i++) {
            book = bookJson[i];
            if(i===0){
                container = document.getElementById("newBooks")
                container.innerHTML = ''
            }
            if(i===7){
                container = document.getElementById("hotBooks")
                container.innerHTML = ''
            }
            displayBookInIndex(container, book)
        }
    }
}
req.send(params.toString());


function displayBookInIndex(bookContainer, book){
    const li = "<li></li>";
    bookContainer.insertAdjacentHTML('beforeend', li);

    const bookElement = bookContainer.lastElementChild
    // console.log(book)

    // 在这里添加书籍信息的 HTML 结构
    let img;
    if (book["have_cover_pic"] == 1.0) {
        img = "../img/cover/" + book["serial_number"].toString() + ".jpg"
    } else {
        img = "../img/noCover.jpg"
    }
    let index = book["index"];

    bookElement.innerHTML = `
                <a href="" onclick="toDetails(${index})">
                    <img src=${img} alt="">
                    <span>
<!--                    ${book["book_name"]}-->
                </span>
                </a>
              `;
}
function toDetails(index){
    window.open(`details.html?bookId=${index}`)
}

