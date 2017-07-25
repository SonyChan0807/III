

$(document).ready(function () {

    var mySlider = $("#yearslider").slider({
        // tooltip: 'always',
        ticks: [2008, 2009, 2010, 2011, 2012, 2013, 2014, 2015, 2016, 2017],
        ticks_labels: [2008, 2009, 2010, 2011, 2012, 2013, 2014, 2015, 2016, 2017],
        ticks_snap_bounds: 30
    });

    // var mySlider = $("#yearSlide").slider();

    // console.log(mySlider);
    renderPage();

    $('li').mouseup(function (e) {
        e.preventDefault();
        $('li').not(this).removeClass('active').removeClass('currentBrand');
        $(this).addClass('active').addClass('currentBrand');;
        var years = mySlider.slider('getValue');
        var brand  =  $(this).find(':first-child').text();
        renderPage(brand, years);
    });
    
    $('#brandDropDown').change(function(){
        var years = mySlider.slider('getValue');
        var brand  =  $(this).find(':first-child').text();
        renderPage(brand, years)});

    mySlider.on('slideStop', function(){
        var years = $(this).slider('getValue');
        var brand  =  $('li.currentBrand').find(':first-child').text();
        console.log(brand);
        console.log(years);
        renderPage(brand, years)});
});



function renderPage(brand, years){
    years = years || 2017;
    brand  =  brand || 'BENZ';
    $('#brandDropDown').text(brand);
    // alert('render');
    console.log(years);
    console.log(brand);
    $.ajax({
        type: "GET",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        url: 'http://localhost:4200/api/chartData',
        data: {
            brand: brand.substring(0,1) + brand.substring(1).toLowerCase(),
            years: years
        },
        success: function (response) {
            console.log(response);
            console.log(response.radarData);
            drawWC(response.wcOption, response.wcData);
            drawRadar(response.radarOption, response.radarData, brand);
        },
        error: function (error) {
            console.log(error)
        }

        });
}


function drawWC(option, rawData) {
    var wordCloudPlot = echarts.init(document.getElementById('wordCloud'));

    option.option.series[0].data = rawData.data;
    wordCloudPlot.setOption(option.option);


    // $(window).on('resize', function () {
    //   if (wordCloudPlot != null && wordCloudPlot != undefined) {
    //     wordCloudPlot.resize();
    //   }
    // });
}

function drawRadar(option, rawData, brand) {
    var radarChart = echarts.init(document.getElementById('radar'));
    
    var data = [];
    var radar = rawData.radar;
    for(var i= 0; i < radar.length; i++){
        console.log(radar[i])
        data.push(radar[i].level);
    }
    option.option.series[0].data[0].value = data;
    option.option.series[0].data[0].name = brand;

    console.log(option)
    radarChart.setOption(option.option);
    // $(window).on('resize', function () {
    //   if (radarChart != null && radarChart != undefined) {
    //     radarChart.resize();
    //   }
    // });
}
