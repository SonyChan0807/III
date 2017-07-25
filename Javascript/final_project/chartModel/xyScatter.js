exports.option = {
    title : {
        text: '價格vs.里程數分佈圖',
        left:'center',
        top:0,
        textStyle:{fontSize: '18'},
        itemGap:0
    },
    grid: {
        show:true,
        left: '7%',
        right: '7%',
        bottom: '7%',
        containLabel: true
    },
    backgroundColor:'rgb(256, 256, 256)', //加上背景色
    tooltip : { //移動到資料點上顯示數值


        showDelay : 0,
        formatter : function (params) {
            if (params.value.length > 1) {
                return params.seriesName + ' :<br/>'
                + params.value[0] + '萬公里 '
                + params.value[1] + '萬元';
            }
            else {
                return params.seriesName + ' :<br/>'
                + params.name + ' : '
                + params.value + '萬公里 ';
            }
        },
        axisPointer:{ //移動到資料點上顯示數值在軸上
            show: true,
            type : 'cross',
            lineStyle: {
                type : 'dashed',
                width : 1
            }
        }
    },
    toolbox: {
        show: false,
        feature: {
            dataZoom: {},
            // brush: {
            //     type: ['rect', 'polygon', 'clear']
            // }
        }
    },
    brush: {
    },
    legend: {
        data: ['預測','出售中物件'],
        left: 'right',
        top:20,
        textStyle:{fontSize:'18'}
    },
    xAxis : [
        {
            name:'萬公里',
            nameLocation: 'middle',
            nameTextStyle:{fontSize:'20'},
            nameGap:'40',
            type : 'value',
            scale:true,
            axisLabel : {
                formatter: '{value}',
                textStyle:{fontSize:'18'}
            },
            splitLine: {
                show: false
            }
        }
    ],
    yAxis : [
        {   
            name:'萬元',
            nameLocation: 'middle',
            nameTextStyle:{fontSize:'20'},
            nameGap:'40',
            type : 'value',
            scale:true, //不從0開始
            axisLabel : {
                formatter: '{value} ',
                textStyle:{fontSize:'18'}
            },
            splitLine: {
                show: false
            },

        }
    ],
    series : [
        {   z:2,
            name:'預測',
            type:'scatter',
            symbolSize:30,
            // insert target data here data:[[]]

        },
        {   z:1,
            name:'出售中物件',
            type:'scatter',
            symbolSize:15,
            // insert target data here data:[[]]
            markArea: {
                silent: true,
                itemStyle: {
                    normal: {
                        color: 'rgba(0,0,0,0.1)',
                        borderWidth: 1,
                        borderType: 'dashed'
                    }
                },
                label:{normal:{textStyle:{fontSize:'18'}}},
                data: [[{
                    name: '',
                    xAxis: 'min',
                    yAxis: 'min'
                }, {
                    xAxis: 'max',
                    yAxis: 'max'
                }]]
            },
            markPoint : {
                 itemStyle:{normal:{opacity:0.9}},
                symbolSize:100,
                data : [

                    {type : 'max', name: '最大值', label:{normal:{textStyle:{fontSize:'18'}}}},
                    {type : 'min', name: '最小值', label:{normal:{textStyle:{fontSize:'18'}}}}
                ]
            },

        }
    ]

    }