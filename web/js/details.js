const paramsStr = window.location.search
const paramsFromUrl = new URLSearchParams(paramsStr)
let bookId = paramsFromUrl.get("bookId") // list

fetchBookDetails()
fetchSimilarBook()

function fetchRandomBook(){
    let sqlString = getRandomBooks(8);
    const params = new URLSearchParams({
        "sql": sqlString
    });
    let req = new XMLHttpRequest();
    req.open("POST", "/PBRS/searchAny");
    req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    let bookJson = ""

    req.onreadystatechange = () => {
        if (req.readyState === 4 && req.status === 200) {
            let bookJson = JSON.parse(req.responseText)
            let len = Object.keys(bookJson).length

            displaySimilarBook(bookJson)
        }
    }
    req.send(params.toString());
}

function displaySimilarBook(bookJson){
    let book;
    let container;
    let len = Object.keys(bookJson).length

    container = document.getElementById("extraBooks")
    container.innerHTML = ''

    for (let i = 0; i < len; i++) {
        book = bookJson[i];
        displayBookInIndex(container, book)
    }
}

function fetchSimilarBook(){
    let req = new XMLHttpRequest();
    req.open("GET", "/PBRS/getSimilarBook?bookId="+bookId);
    req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

    req.onreadystatechange = () => {
        if (req.readyState === 4 && req.status === 200) {
            let bookJson = JSON.parse(req.responseText)
            let len = Object.keys(bookJson).length

            if (len === 0){
                fetchRandomBook()
            }
            displaySimilarBook(bookJson)
        }
    }
    req.send();
}


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


function fetchBookDetails() {
    let req = new XMLHttpRequest();
    req.open("GET", "/PBRS/getOneBook?bookId="+bookId);

    req.onreadystatechange = ()=>{
        if (req.readyState === 4 && req.status === 200){
            let book = JSON.parse(req.responseText)
            // console.log(book)

            // 在这里添加书籍信息的 HTML 结构
            let img;
            if (book["have_cover_pic"]==1.0){
                img = "../img/cover/"+book["serial_number"].toString()+".jpg"
            } else {
                img = "../img/noCover.jpg"
            }
            let image = document.getElementById("bookImage")
            image.src = img;

            let dataParamSet = [
                "book_name",
                "author",
                "awards",
                "age_appropriate",
                "introduction",
            ];
            for (const id of dataParamSet) {
                let elm = document.getElementById(id);
                elm.innerText = book[id]
            }
        }
    }
    req.send();

}
