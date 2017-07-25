var radarChart = echarts.init(document.getElementById('radar'));
    var option = {
                  title: {
                    // text: '多雷达图'
                  },
                  tooltip: {
                    trigger: 'axis'
                  },
                  legend: {
                    // x: 'center',
                    // data: ['某软件', '某主食手机', '某水果手机', '降水量', '蒸发量']
                  },
                  radar: [{
                            indicator: [{
                                text: '安全',
                                
                                max: 100
                              },
                              {
                                text: '省油',
                                max: 100
                              },
                              {
                                text: '外觀',
                                max: 100
                              },
                              {
                                text: '保值',
                                max: 100
                              },
                              {
                                text: '舒適',
                                max: 100
                              }
                            ],
                            // center: ['25%', '40%'],
                            // radius: 80
                            name:{
                              textStyle:{
                                fontSize:20,
                                color:'#000'
                              }   
                            }
                          }

                        ],
                  series: [{
                      type: 'radar',
                        tooltip: {
                          trigger: 'item'
                        },
                      itemStyle: {
                        normal: {
                          areaStyle: {
                            type: 'default'
                          }
                        }
                      },
                      data: [{
                        value: [60, 73, 85, 40],
                        name: '某软件'
                      }]
                    }

                  ]
    };
    radarChart.setOption(option);
    // $(window).on('resize', function () {
    //   if (radarChart != null && radarChart != undefined) {
    //     radarChart.resize();
    //   }
    // });