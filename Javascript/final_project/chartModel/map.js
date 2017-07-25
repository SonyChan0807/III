exports.option = {
        title: {
            text: '全國出售中物件數量分佈圖',
            left: 'center'
        },
        tooltip: {
            trigger: 'item',
            showDelay: 0,
            transitionDuration: 0.2,
            formatter: function (params){
                console.log(params)
                if(params.value){
                    return params.name + '<br/> 共: ' + params.value;   
                }else{
                    return params.name + '<br/> 共: 0';   
                }
            }
        },
        visualMap: {
            left: 'right',
            min: 0,
            max: 40,
            inRange: {
                color: ['#313695', '#4575b4', '#74add1', '#abd9e9', '#e0f3f8', '#ffffbf', '#fee090', '#fdae61', '#f46d43', '#d73027', '#a50026']
            },
            text:['High','Low'],           // 文本，默认为数值文本
            calculable: true
        },
        toolbox: {
            show: true,
            //orient: 'vertical',
            left: 'left',
            top: 'top',
            feature: {
                dataView: {readOnly: false},
                restore: {},
                saveAsImage: {}
            }
        },
        series: [
            {
                name: '',
                type: 'map',
                roam: true,
                map: 'Taiwan',
                itemStyle:{
                    emphasis:{label:{show:true}}
                },
                // Insert data here data:[{}];
            }
        ]
    };
