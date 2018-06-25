
//srcipt标签式引入
	var myChart = echarts.init(document.getElementById('main'));

	myChart.hideLoading();

	//图表使用-------------------
	var option = {
		title : {
			text : '统计管理',
			subtext : ''
		},
		tooltip : {
			trigger : 'axis'
		},
		legend : {
			data : [ '看视频', '购买' ]
		},
		toolbox : {
			show : true,
			feature : {
				mark : {
					show : true
				},
				dataView : {
					show : true,
					readOnly : false
				},
				magicType : {
					show : true,
					type : [ 'line', 'bar', 'stack', 'tiled' ]
				},
				restore : {
					show : true
				},
				saveAsImage : {
					show : true
				}
			}
		},
		calculable : true,
		xAxis : [ {
			type : 'category',
			boundaryGap : false,
			data : time,
			name :"日期"
		} ],
		yAxis : [ {
			type : 'value',
			name : "人数"
		} ],
		series : [ {
			name : '看视频',
			type : 'line',
			smooth : true,
			itemStyle : {
				normal : {
					areaStyle : {
						type : 'default'
					}
				}
			},
			data : ccount
		},{
			name : '购买',
			type : 'line',
			smooth : true,
			itemStyle : {
				normal : {
					areaStyle : {
						type : 'default'
					}
				}
			},
			data : pcount
		} ]
	};
	myChart.setOption(option);
	
	//srcipt标签式引入
	var myChart2 = echarts.init(document.getElementById('main2'));
	myChart2.hideLoading();

	//图表使用-------------------
	var option2 = {
		title : {
			text : '一天内的统计图（以开始时间为准）',
			subtext : ''
		},
		tooltip : {
			trigger : 'axis'
		},
		legend : {
			data : [ '人数' ]
		},
		toolbox : {
			show : true,
			feature : {
				mark : {
					show : true
				},
				dataView : {
					show : true,
					readOnly : false
				},
				magicType : {
					show : true,
					type : [ 'line', 'bar', 'stack', 'tiled' ]
				},
				restore : {
					show : true
				},
				saveAsImage : {
					show : true
				}
			}
		},
		calculable : true,
		xAxis : [ {
			type : 'category',
			boundaryGap : false,
			data : hours,
			name :"24小时制"
		} ],
		yAxis : [ {
			type : 'value',
			name :"人数"
		} ],
		series : [ {
			name : '人数',
			type : 'line',
			smooth : true,
			itemStyle : {
				normal : {
					areaStyle : {
						type : 'default'
					}
				}
			},
			data : ocount
		}]
	};
	myChart2.setOption(option2);
	
	