const mysql = require('mysql2/promise');
const mongoose = require('mongoose');
const Textcloud = mongoose.model('textCloud');
const Radar = mongoose.model('radar');
const child = require('async-child-process');

var xyScatterOption = require("../chartModel/xyScatter");
var mapOption = require("../chartModel/map");
var radarOption = require("../chartModel/radar");
var wcOption = require("../chartModel/wordCloud");
var taiwan = require("../chartModel/taiwan");



const rRoot = process.env.R_ROOT
const mysqlConfig = {
    host: process.env.MYSQL_HOST,
    port: process.env.MYSQL_PORT,
    user: process.env.MYSQL_USER,
    password: process.env.MYSQL_PW,
    database: process.env.MYSQL_DATABASE
}


//  ===========================================

// 二手車估價下拉選單

exports.dropdownQuery = async(req, res) => {
    let data = req.query;
    console.log(data);
    const connection = await mysql.createConnection(mysqlConfig);

    let queryStr
    if (data.brand && data.model && data.years) {
        queryStr = `select distinct(type) from newCarView where brand="${data.brand}" and model="${data.model}" and years="${data.years}" order by type`
        console.log(queryStr)
    } else if (data.brand && data.years) {
        queryStr = `select distinct(model) from newCarView where brand="${data.brand}" and years="${data.years}" order by model`
        console.log(queryStr)
    } else if (data.brand) {
        queryStr = `select distinct(years) from newCarView where brand="${data.brand}" and years >= 2008 order by years`
        console.log(queryStr)
    } else {
        queryStr = `select distinct(brand) from newCarView order by brand`;
        console.log(queryStr)
    }

    const [rows, fields] = await connection.query(queryStr);
    console.log(rows);
    connection.end();

    res.header('Content-type', 'application/json');
    res.json(rows);

}



//  ===========================================

// 二手車車價查詢按鈕

exports.priceQueryButton = async(req, res) => {
    let sellingData = {}
    let data = req.query;
    let brand = data.brand;
    let years = data.years;
    let model = data.model;
    let type = data.type;
    let mileage = parseInt(data.mileage);
    console.log(`${brand}_${years}_${model}_${type}`)
    const connection = await mysql.createConnection(mysqlConfig);

    // Get New Car Data
    const [carInfo, info_fields] = await connection.query(`select short_intro, img_url, tag_price from newCarView where brand="${brand}" and model="${model}" and years="${years}" and type="${type}"`);

    // 回歸公式
    const commandStr = `Rscript ${rRoot} ${years} ${brand} ${mileage}`
    console.log(commandStr);
    const std = await child.execAsync(commandStr);
    console.log(std);
    let depreRate = parseFloat(std.stdout.replace('/n', '').substr(4));
    let tagPrice = parseFloat(carInfo[0].tag_price);
    let estPrice = (depreRate * tagPrice).toFixed(1);
    sellingData.estPrice = estPrice;


    // 新車評論
    sellingData.intro = carInfo[0].short_intro;


    // 舊車資料
    const [usedCars, usedCars_fields] = await connection.query(`select * from usedCarView where brand="${brand}" and model="${model}" and years="${years}" and type="${type}" and mileage > 0 and price > 0 `);

    console.log(usedCars)
    
    // 回歸資料點
    xyScatterOption.option.series[0].data = [[(mileage / 10000).toFixed(1), estPrice]];

    // 其他資料點
    let xyData = [];
    let tableData = [];
    for (var key in usedCars) {
        let xyArray = [];
        let tableObj = {};
        let mileage = usedCars[key].mileage / 10000
        let price = parseFloat(usedCars[key].price).toFixed(1);

        // xyScatter
        xyArray.push(mileage.toFixed(1))
        xyArray.push(price);
        xyData.push(xyArray);

        // table
        tableObj.url = usedCars[key].url;
        tableObj.color = usedCars[key].color
        tableObj.location = usedCars[key].location
        tableObj.source = usedCars[key].source
        tableObj.mileage = mileage.toFixed(1);
        tableObj.price = price;
        tableData.push(tableObj);
    }
    
    xyScatterOption.option.series[1].data = xyData;
    sellingData.XYoption = xyScatterOption.option;
    sellingData.tableData = tableData;

    // 圖片url
    let host_url = 'https://s3-ap-northeast-1.amazonaws.com/bb101finalproject/img/'
    let img_str = encodeURI(`${data.years}_${data.brand}_${data.model}_${data.type}.png`.replace(' ', '_').replace('/', '_'));
    let url_str = host_url + img_str
    if (carInfo[0].img_url) {
        sellingData.imgURL = url_str;
    } else {
        sellingData.imgURL = host_url + 'no_img.png'
    }

    // 地圖資料
    const [mapCars, mapCars_fields] = await connection.query(`select location, avg(price) avg, count(*) count from usedCarView 
                                                                where brand="${brand}" and model="${model}" and years="${years}" 
                                                                and type="${type}" and mileage > 0 and price > 0 group by location order by location`);
    console.log(mapCars);

    let mapData = [];
    for (key in mapCars) {
        let mapObj = {}
        mapObj.name = mapCars[key].location;
        mapObj.avg = parseFloat(mapCars[key].avg).toFixed(1);
        mapObj.value = mapCars[key].count;
        console.log(mapObj);
        mapData.push(mapObj);
    }
    mapOption.option.series[0].data = mapData;

    sellingData.mapJSON = taiwan.taiwanJSON;
    sellingData.mapOption = mapOption.option;
    connection.end();
    res.json(sellingData)

}

// exports.




//  ===========================================


// // 排行榜
// exports.priceRanking = async(req, res) => {
//     order = req.params.order
//     console.log(order)
//     let queryStr
//     if (order === "asce") {
//         queryStr = `select `
//     } else if (order === "desc") {
//         queryStr = `select `
//     }

//     res.send(order);
// }


//  ===========================================

// 文字雲 雷達圖

exports.chartData = async(req, res) => {
    let chartData = {}
    const query = req.query
    const wordCloudPromise = Textcloud.findOne({
        "Board": query.brand,
        "year": parseInt(query.years)
    }, {"_id": 0, "Board": 0, "year": 0})

    const radarDataPromise =  Radar.findOne({
        "Brand": query.brand,
        "year": parseInt(query.years)
    },{"_id": 0, "Brand": 0, "year": 0});
    // const wordCloud = await Textcloud.findOne({"year":parseInt(data.years)});

    const [wordCloud, radarData] = await Promise.all([wordCloudPromise, radarDataPromise]);
    console.log(wordCloud);
    console.log(radarData);


    
    chartData.wcOption= wcOption;
    chartData.radarOption = radarOption;
    chartData.wcData = wordCloud;
    chartData.radarData = radarData;


    res.json(chartData)
}

