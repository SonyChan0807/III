function init() {
    $('#brand').selectpicker('refresh');
    $('#years').selectpicker('refresh');
    $('#model').selectpicker('refresh');
    $('#type').selectpicker('refresh');


    // Get Brand data
    genBrand();

    // Generate years option
    document.getElementById('brand').addEventListener('change', genYears, false);
    document.getElementById('years').addEventListener('change', genModel, false);
    document.getElementById('model').addEventListener('change', genType, false);
    document.getElementById('submitBtn').addEventListener('click', renderPage, false);
    // document.getElementById('brand').addEventListener('change',genYears, false);

}

function genBrand() {
    $.ajax({
        type: "GET",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        url: 'http://localhost:4200/api/queryPrice',
        success: function (response) {
            var opt = ''
            for (var item in response) {
                opt += '<option>' + response[item].brand + '</option>'
            }
            $('#brand').append(opt);

            $('#brand').selectpicker('refresh');
        },
        error: function (error) {
            console.log(error)
        }

    });
}

function genYears() {
    var brandStr = $('#brand option:selected').text()
    console.log(brandStr);
    $('#years').empty().append('<option value="years">--請選擇年份--</option>');
    $('#model').empty().append('<option value="years">--請選擇車款--</option>');
    $('#type').empty().append('<option value="type">--請選擇等級--</option>');
    $('#mileage').val("");
    $.ajax({
        type: "GET",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        url: 'http://localhost:4200/api/queryPrice',
        data: {
            brand: brandStr
        },
        success: function (response) {
            var opt = '';
            for (var item in response) {
                opt += '<option>' + response[item].years + '</option>'
            }
            $('#years').append(opt);
            $('#years').selectpicker('refresh');
        },
        error: function (error) {
            console.log(error)
        }
    });
    // $('#brand').selectpicker('refresh');
    $('#years').selectpicker('refresh');
    $('#model').selectpicker('refresh');
    $('#type').selectpicker('refresh');
}

function genModel() {
    var brandStr = $('#brand option:selected').text()
    var yearsStr = $('#years option:selected').text()
    $('#model').empty().append('<option value="years">--請選擇車款--</option>');
    $('#type').empty().append('<option value="type">--請選擇等級--</option>');
    $('#mileage').val("");
    $.ajax({
        type: "GET",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        url: 'http://localhost:4200/api/queryPrice',
        data: {
            brand: brandStr,
            years: yearsStr
        },
        success: function (response) {
            var opt = '';
            for (var item in response) {
                opt += '<option>' + response[item].model + '</option>'
            }
            $('#model').append(opt);
            $('#model').selectpicker('refresh');
        },
        error: function (error) {
            console.log(error)
        }
    });
    // $('#brand').selectpicker('refresh');
    // $('#years').selectpicker('refresh');
    $('#model').selectpicker('refresh');
    $('#type').selectpicker('refresh');
}

function genType() {
    var brandStr = $('#brand option:selected').text()
    var yearsStr = $('#years option:selected').text()
    var modelStr = $('#model option:selected').text()
    console.log(yearsStr);
    $('#type').empty();
    $('#mileage').val("");
    $.ajax({
        type: "GET",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        url: 'http://localhost:4200/api/queryPrice',
        data: {
            brand: brandStr,
            years: yearsStr,
            model: modelStr
        },
        success: function (response) {
            var opt = '<option>請選擇等級</option>';
            for (var item in response) {
                opt += '<option>' + response[item].type + '</option>'
            }
            console.log(opt);
            $('#type').append(opt);

            $('#type').selectpicker('refresh');
        },
        error: function (error) {
            console.log(error)
        }
    });
    $('#type').selectpicker('refresh');
}

// =========================== ＱueryPrice ==============================


function renderPage(event) {
    event.preventDefault();
    var brandStr = $('#brand option:selected').text()
    var yearsStr = $('#years option:selected').text()
    var modelStr = $('#model option:selected').text()
    var typeStr = $('#type option:selected').text()
    var mileage = $('#mileage').val();


    // if(brandStr && yearsStr && modelStr && typeStr && mileage != 0){
        $.ajax({
            type: "GET",
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            url: 'http://localhost:4200/api/getEstPrice',
            data: {
                brand: brandStr,
                years: yearsStr,
                model: modelStr,
                type: typeStr,
                mileage: mileage
                // brand: 'NISSAN',
                // years: '2008',
                // model: 'LIVINA',
                // type: '1.6 B',
                // mileage: 100000

            },
            success: function (response) {
                showEstPrice(response.estPrice, brandStr, yearsStr, modelStr, typeStr, mileage);
                showIntro(response.imgURL,response.intro);
                showTable(response.tableData);
                drawXYScatter(response.XYoption);
                drawMap(response.mapJSON, response.mapOption);
            },
            error: function (error) {
                console.log(error)
            }
        });
    }


// }

function showIntro(imgURL,shortIntro){
    $('#carImg').attr('src', imgURL);
    $('#shortComment').empty().append('<h2>特色描述</h2>').append('<p>' + shortIntro + '</p>');
}

function showEstPrice(estPrice, brandStr, yearsStr, modelStr, typeStr, mileage){
    // alert('test');
    // console.log(brandStr);
    // console.log(mileage);
    $('#summaryTitle1').text(brandStr + '   ' + yearsStr + '   ' + modelStr);
    $('#summaryTitle2').text(typeStr + '   (' + (mileage / 10000).toFixed(1) + '萬公里)');
    $('#summaryPrice').text('   ' + estPrice + '萬');


}

function showTable(tableData) {
    $('#item-table > tbody > tr').empty();
    for (var key in tableData) {
        var tableStr = '<tr><td>$' + tableData[key].price + '</td>' +
            '<td>' + tableData[key].mileage + '</td>' +
            '<td>' + tableData[key].color + '</td>' +
            '<td>' + tableData[key].location + '</td>' +
            '<td>' + tableData[key].source + '</td>' +
            '<td><a href="' + tableData[key].url + '" class="btn btn-xs btn-info">go</a></td></tr>';

        $('#item-table tbody').append(tableStr);
    }
}


function drawMap(taiwanJSON, option) {
    // console.log(option);
    // $('#map').empty();
    // if($('#mapImg').exists()){
    //     $('#mapImg').remove();
    // }
    var chart = echarts.init(document.getElementById('map'));
    // chart.hideLoading();

    echarts.registerMap('Taiwan', taiwanJSON);

    chart.setOption(option);
}

function drawXYScatter(option){
    var XYscatter = echarts.init(document.getElementById('XYscatter'));
    XYscatter.setOption(option);

    // $(window).on('resize', function () {
    // if (XYscatter != null && XYscatter != undefined) {
    // XYscatter.resize();
    // }
}



window.addEventListener('load', init, false);