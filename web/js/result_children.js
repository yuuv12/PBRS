const sqlString = localStorage.getItem("Sql4Children")
const params = new URLSearchParams({
    "sql": sqlString
});


const bookContainer = document.getElementById("book-list")

let req = new XMLHttpRequest();
req.open("POST", "/PBRS/searchAny");
req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

req.onreadystatechange = () => {
    if (req.readyState === 4 && req.status === 200) {
        let bookJson = JSON.parse(req.responseText)
        bookContainer.innerHTML = '';
        let len = Object.keys(bookJson).length
        if (len > 50) {
            len = 50
        }

        let numCounter = document.getElementById("count-num");

        let book;
        for (let i = 0; i < len; i++) {
            book = bookJson[i];
            displayBookInChildren(book)
        }
        numCounter.innerText="共找到"+len+"本书！"
    }
}
req.send(params.toString());


function displayBookInChildren(book) {
    let index = book["index"];
    const div = "<div class='book'></div>";			//字符串类型
    bookContainer.insertAdjacentHTML('beforeend', div);	//插入元素内部的最后一个子节点之后

    const bookElement = bookContainer.lastElementChild

    // 在这里添加书籍信息的 HTML 结构
    let img;
    if (book["have_cover_pic"] == 1.0) {
        img = "../img/cover/" + book["serial_number"].toString() + ".jpg"
    } else {
        img = "../img/noCover.jpg"
    }

    let characters =
        book["main_character_animals"] +
        book["main_character_plants"] +
        book["main_character_figures"] +
        book["main_character_foods"] +
        book["main_character_vehicles"] +
        book["main_character_others"]
    let id = "likeIcon-"+book["index"];

    bookElement.innerHTML = `
                <img src=${img} alt="书籍封面" class="cover" onclick="toDetails(${index})">
                <div class="info" data-bookname="${book["book_name"]}" data-index="${book["index"]}">
                    <p><span>书名：</span>${book["book_name"]}</p>
                    <p><span>主人公形象：</span>${characters}</p>
                    <p><span>主人公特点：</span>${book["protagonist_features"]}</p>
                    <p><span>主人公关系：</span>${book["protagonist_relations"]}</p>
                    <p><span>时间：</span>${book["time_factors"]}</p>
                    <p><span>地点：</span>${book["location_factors"]}</p>
                    <img src="../img/like.svg" alt="" class="like_it" title="喜欢这本书！" id="${id}" onclick="likeIt('${id}')">
                </div>
              `;
}


function likeIt(id){
    const elm = document.getElementById(id);

    if (id === "favorites"){
        if (elm.src.includes('like.svg')) {
            elm.src = '../img/like_clicked.svg';
        } else {
            elm.src = '../img/like.svg';
        }

        return;
    }

    const book = elm.parentElement;
    const book_name = book.dataset.bookname;

    const index = book.dataset.index;

    let item = {
        "book_name":book_name,
        "index":index,
    }
    let favorites = JSON.parse(localStorage.getItem("favorites")) || [];

    if (elm.src.includes('like.svg')) {
        elm.src = '../img/like_clicked.svg';  // 如果当前是空心，切换为实心

        if (!favorites.includes(item)) {
            favorites.push(item);
            localStorage.setItem("favorites", JSON.stringify(favorites))
        }
    } else {
        elm.src = '../img/like.svg';  // 如果当前是实心，切换为空心

        favorites = favorites.filter(fav => fav["book_name"] !== book_name);
        localStorage.setItem("favorites", JSON.stringify(favorites))
    }
};


function loadChosenElements() {
    const chosenElements = document.querySelectorAll('ul .chosen');
    chosenElements.forEach(el => el.remove());

    const chosenHTML = JSON.parse(localStorage.getItem('chosenElements') || '[]');
    const container = document.getElementById('chosen-container'); // 在下一个页面上准备一个容器
    const lastElm = document.getElementById("like")

    chosenHTML.forEach(html => {
        const tempDiv = document.createElement('div');
        tempDiv.innerHTML = html;
        const newElement = tempDiv.firstElementChild;
        container.insertBefore(newElement, lastElm.previousSibling);
    });
}

// 在页面加载完成后调用这个函数
window.addEventListener('DOMContentLoaded', loadChosenElements);

function toDetails(index) {
    window.open(`details.html?bookId=${index}`)
}

function myFavorite(){
    let favoritesList = document.getElementById("favoritesList")
    favoritesList.innerHTML = ""; // 清空现有列表
    let favorites = JSON.parse(localStorage.getItem("favorites")) || [];

    if (favorites.length === 0) {
        favoritesList.innerHTML = "<li>收藏夹为空</li>";
    } else {
        favorites.forEach(item => {
            let li = document.createElement("li");
            li.onclick = function (){
                toDetails(item["index"])
            }
            li.textContent = "· "+item["book_name"];
            favoritesList.appendChild(li);
        });
    }
}