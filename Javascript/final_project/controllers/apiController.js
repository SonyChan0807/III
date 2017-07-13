const mysql = require('mysql2/promise');
const mongoose = require('mongoose');
const Textcloud = mongoose.model('textCloud')
const Radar = mongoose.model('radar')


mysqlConfig = {
        host: process.env.MYSQL_HOST,
        port: process.env.MYSQL_PORT,
        user: process.env.MYSQL_USER,
        password: process.env.MYSQL_PW,
        database: process.env.MYSQL_DATABASE
    }


//  ===========================================

// 二手車估價下拉選單

exports.dropdownQuery= async (req,res) => {
    let data = req.query;
    console.log(data);
    const connection = await mysql.createConnection(mysqlConfig);

    let queryStr
    if(data.brand && data.model && data.years){
        queryStr = `select * from final_carPrice where brand=${data.brand} and model=${data.model} and years=${data.years}`
        console.log(queryStr)
    }else if(data.brand && data.model){
        queryStr = `select * from final_carPrice where brand=${data.brand} and model=${data.model}`
        console.log(queryStr)
    }else if(data.brand){
        queryStr = `select * from final_carPrice where brand=${data.brand}`
        console.log(queryStr)
    }
    
    const [rows, fields] = await connection.query(queryStr);
    console.log(rows);
    connection.end();
    
    res.header('Content-type','application/json');
    res.json(rows);
    
    console.log(data);
}

// 首頁估價按鈕

exports.homeQueryPrice = async (req, res) => {
    let data = req.query
    res.render('./page/price')

}

//  ===========================================

// 二手車車價查詢按鈕

exports.priceQuerybutton = async (req, res) => {
    // 回歸公式

    // 回歸公式座標

    // 新車評論

    // 圖片url

    // 分群推薦
    
    // 地圖資料
    
    
}




//  ===========================================


// 排行榜
exports.priceRanking =  async (req, res) => {
    order = req.params.order
    console.log(order)
    let queryStr
    if(order === "asce"){
        queryStr = `select `
    }else if(order === "desc"){
        queryStr = `select `
    }

    res.send(order);
}


//  ===========================================

// 文字雲

exports.textCloud = async (req, res) => {
    const data = req.query
    console.log(data)
    const textCloud = await Textcloud.findOne({"Board": data.brand, "year": parseInt(data.years)})
    // const textCloud = await Textcloud.findOne({"year":parseInt(data.years)});

    res.json(textCloud)
}

// 雷達圖

exports.radarData = async (req, res) => {
    const data = req.query
    console.log(data)
    const radarData = await Radar.findOne({"Board": data.brand, "year": parseInt(data.years)});
    res.json(radarData)
}