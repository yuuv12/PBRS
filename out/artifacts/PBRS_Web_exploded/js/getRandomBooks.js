function generateRandomInteger() {
    // 生成 1 到 1000 之间的随机整数
    return Math.floor(Math.random() * 1000) + 1;
}
function getRandomBooks(num){
    let indexes = [];
    for (let i = 0; i < num; i++) {
        indexes.push(generateRandomInteger())
    }

    let baseQuery = "SELECT * FROM data WHERE "
    const conditions = [];

    Object.entries(indexes).forEach(idx => {
        let id = parseInt(idx[1]);
        let condition = `\`index\` = ${id}`;
        conditions.push(`(${condition})`);
    });

    if (conditions.length > 0) {
        baseQuery += "(" + conditions.join(" OR ") + ")" + " AND (have_cover_pic = 1)";
    }
    // console.log(baseQuery)
    return baseQuery;
}
