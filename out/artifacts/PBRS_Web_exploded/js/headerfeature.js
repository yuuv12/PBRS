document.addEventListener('DOMContentLoaded', function () {
    const featureItems = document.querySelectorAll('.featureitem');
    const navImages = document.querySelectorAll('.nav ul li a .featureimage');

    featureItems.forEach(item => {
        item.addEventListener('click', function () {
            const featureType = this.closest('.onefeature').id; // 获取 oneFeature 的 ID
            const featureImageSrc = this.querySelector('img').src; // 获取被点击 featureitem 的图片源
            const featureAlt = this.querySelector('img').alt;
            const featureClassName = this.querySelector('img').className;

            // 找到匹配的 nav 元素
            document.querySelectorAll('.features').forEach((feature, index) => {
                if (feature.dataset.target === featureType) {
                    // 更新对应 nav 中的图片
                    navImages[index].src = featureImageSrc;
                    navImages[index].alt = featureAlt;
                    navImages[index].className = featureClassName;
                }
            });
        });
    });
});

// 定义一个函数来读取图像并生成 JSON 对象
function searchChildren() {
    // 获取所有带有 class 的 img 元素
    const imgElements = document.querySelectorAll('.nav img[class]');

    // 初始化一个空对象
    const imageJSON = {};

    // 遍历所有 img 元素
    imgElements.forEach(img => {
        // 获取 class 和 alt 属性
        const imgClass = img.className;
        const imgAlt = img.alt;

        if (!(imgClass === "default" || imgClass === "hover" || imgClass === "featureimage")){
            // 将 class 作为键，alt 作为值存储在 JSON 对象中
            imageJSON[imgClass] = imgAlt;
        }
    });

    let baseQuery = "SELECT * FROM data WHERE 1=1"
    const conditions = [];

    Object.entries(imageJSON).forEach(([key, value]) => {
        let condition;
        switch (true) {
            case key.startsWith("main_character"):
                condition = `${key} IS NOT NULL`;
                break;
            default:
                condition = `${key} LIKE '%${value}%'`;
        }
        conditions.push(`(${condition})`);
    });

    if (conditions.length > 0) {
        baseQuery += " AND " + conditions.join(" AND ");
    }


    localStorage.setItem("Sql4Children", baseQuery);

    const chosenElements = document.querySelectorAll('.chosen');
    const chosenHTML = Array.from(chosenElements).map(el => el.outerHTML);
    localStorage.setItem('chosenElements', JSON.stringify(chosenHTML));

    window.location.href = "search_result_children.html"

}
