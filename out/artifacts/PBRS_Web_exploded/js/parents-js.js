const bookContainer = document.getElementById('container_parents');
const facet = document.getElementById("facet");

function toDetails(index){
    window.open(`details.html?bookId=${index}`)
}

function displayBookInParent(book){
    let index = book["index"];
    const div = `<div class="book-item" onclick="toDetails(${index})"></div>`;			//字符串类型
    bookContainer.insertAdjacentHTML('beforeend',div);	//插入元素内部的最后一个子节点之后

    const bookElement = bookContainer.lastElementChild

    // 在这里添加书籍信息的 HTML 结构
    let img;
    if (book["have_cover_pic"]==1.0){
        img = "../img/cover/"+book["serial_number"].toString()+".jpg"
    } else {
        img = "../img/noCover.jpg"
    }

    bookElement.innerHTML = `
                <div class="book-image">
                <img src=${img} alt="Book">
              </div>
              <div class="book-info">
                <div class="fenlei-header">
                  <h2>书籍名称</h2>
                </div>
                <div class="fenlei-neirong">
                  <h3 class="book-name">${book["book_name"]}</h3>
                </div>
                <div class="fenlei-header">
                  <h2>作者</h2>
                </div>
                <div class="fenlei-neirong">
                  <h3 class="book-writer">${book["author"]}</h3>
                </div>
                <div class="fenlei-header">
                  <h2>领域评价</h2>
                </div>
                <div class="fenlei-neirong">
                  <h3 class="book-awards">${book["awards"]}</h3>
                </div>
                <div class="fenlei-header">
                  <h2>适读年龄</h2>
                </div>
                <div class="fenlei-neirong">
                  <h3 class="book-ages">${book["age_appropriate"]}</h3>
                </div>
                <div class="fenlei-header">
                  <h2>内容简介</h2>
                </div>
                <div class="fenlei-neirong">
                  <h3 class="book-jianjie">${book["introduction"]}</h3>
                </div>
              </div>
              `;
}


function parentSearch(inputElmId, args){

    let req = new XMLHttpRequest();
    req.open("POST", "/PBRS/searchString");
    req.setRequestHeader("Content-Type", "application/json");

    let inputElm =  document.getElementById(inputElmId);
    let ask = inputElm.value;
    if (ask.length<=0){
        alert("输入值为空")
        return ;
    }

    let param = {
        "ask": ask,
        "args": args,
    };
    const body = JSON.stringify(param);

    req.onreadystatechange = ()=>{
        if (req.readyState === 4 && req.status === 200){
            let bookJson = JSON.parse(req.responseText)

            let len = Object.keys(bookJson).length
            bookContainer.innerHTML = '<h1>共找到'+len+'本书！</h1>';

            let book;
            for (let i = 0; i<len; i++) {
                book = bookJson[i];
                displayBookInParent(book)
            }
            bookContainer.style.marginTop="30px"
            facet.hidden = false;

        }
    }
    req.send(body);

}


function facetSearch(inputElmId){

        let baseQuery = "";
        // 初始化筛选条件对象
        let filters = {};

        // 获取所有的facet-group
        document.querySelectorAll('.facet-group').forEach(group  => {
            let header = group.querySelector('.facet-header h3').id.trim(); // 获取组标题作为键
            let selectedValues = [];

            // 获取组内所有被选中的复选框的值
            group.querySelectorAll('input[type="checkbox"]:checked').forEach(checkbox => {
                let input = checkbox.closest("label").textContent.trim()
                selectedValues.push(input);
            });

            // 将选择的值添加到filters对象
            if (selectedValues.length > 0) {
                filters[header] = selectedValues;
            }
        });

        let conditions = [];
        for (let key in filters) {
            if (filters[key].length > 0) {
                let condition = filters[key].map(value => {
                    if (value === "全部"){
                        return `1=1`
                    }
                    if (key === "awards") {
                        return `awards LIKE "%${value}%"`;
                    } else if (key === "book_type") {
                        return `book_type = "${value}"`;
                    } else if (key === "is_series") {
                        if (value === "是"){
                            return `is_series = 1`
                        }
                        return `is_series = 0`;
                    } else if (key === "age_appropriate") {
                        return `age_appropriate = "${value}"`;
                    } else if (key === "cover_color") {
                        return `cover_color Like "%${value}%"`;
                    } else if (key === "main_character") {
                        switch (value){
                            case "动物": return `main_character_animals IS NOT NULL`;
                            case "植物": return `main_character_plants IS NOT NULL`;
                            case "人物": return `main_character_figures IS NOT NULL`;
                            case "食物": return `main_character_foods IS NOT NULL`;
                            case "交通工具": return `main_character_vehicles IS NOT NULL`;
                            case "其他": return `main_character_others IS NOT NULL`;
                        }
                    } else if (key === "time_factors") {
                        return `time_factors = "${value}"`;
                    }
                }).join(' OR ');
                conditions.push(`(${condition})`);
            }
        }

    if (conditions.length > 0) {
        baseQuery = conditions.join(" AND ");
    }

    parentSearch("search-parent", baseQuery)
}


const paramsStr = window.location.search
const params = new URLSearchParams(paramsStr)
let ask = params.get("ask")

if (ask !== null){
    const input = document.getElementById("search-parent");
    input.value = ask;
    parentSearch("search-parent", '')
}

