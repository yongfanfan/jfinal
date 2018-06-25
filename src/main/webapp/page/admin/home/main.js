var loadSwitch = [0, 0, 0, 0];
// 基于准备好的dom，初始化echarts实例
var userChart = echarts.init($('#user')[0]);
var browseChart = echarts.init($('#browse')[0]);
var loginTimeChart = echarts.init($('#loginTime')[0]);
var sessionTimeChart = echarts.init($('#sessionTime')[0]);
// 指定图表的配置项和数据
userChart.setOption({
    title: {
        text: '用户数统计',
        x: 'center',
        show: false
    },
    tooltip: {
        trigger: 'axis'
    },
    legend: {
        data: ['新用户', '日活'],
        x: 'left',
        top: '8px',
        left: '20px'
    },
    grid: {
        left: '3%',
        right: '6%',
        bottom: '10%',
        containLabel: true
    },
    dataZoom: [
        {
            type: 'slider',
            start: 0,
            end: 100
        },
        {
            type: 'inside',
            start: 0,
            end: 100
        }
    ],
    xAxis: {
        name: '日期',
        type: 'category',
        boundaryGap: false,
        data: []
    },
    yAxis: {
        name: '个数',
        type: 'value'
    },
    series: [{
        name: '新用户',
        type: 'line',
        smooth: true,
        data: []
    }, {
        name: '日活',
        type: 'line',
        smooth: true,
        data: []
    }]
});
browseChart.setOption({
    title: {
        text: '浏览统计',
        x: 'center',
        show: false
    },
    tooltip: {
        trigger: 'axis'
    },
    legend: {
        data: ['mainPv', 'orderPv'],
        x: 'left',
        top: "8px",
        left: "20px"
    },
    grid: {
        left: '3%',
        right: '6%',
        bottom: '10%',
        containLabel: true
    },
    dataZoom: [
        {
            type: 'slider',
            start: 0,
            end: 100
        },
        {
            type: 'inside',
            start: 0,
            end: 100
        }
    ],
    xAxis: {
        name: '日期',
        type: 'category',
        boundaryGap: false,
        data: []
    },
    yAxis: {
        name: '个数',
        type: 'value'
    },
    series: [{
        name: 'mainPv',
        type: 'line',
        smooth: true,
        data: []
    }, {
        name: 'orderPv',
        type: 'line',
        smooth: true,
        data: []
    }]
});
loginTimeChart.setOption({
    title: {
        text: '登录应用时间段分布',
        subtext: '所选日期区间内分时间段统计',
        x: 'center',
        show: false
    },
    color: ['#3398DB'],
    tooltip: {
        trigger: 'axis',
        axisPointer: {
            type: 'shadow'
        }
    },
    grid: {
        left: '3%',
        right: '6%',
        bottom: '10%',
        containLabel: true
    },
    xAxis: [
        {
            type: 'category',
            data: ['0-2', '2-4', '4-6', '6-8', '8-10', '10-12', '12-14', '14-16', '16-18', '18-20', '20-22', '22-24'],
            axisTick: {
                alignWithLabel: true
            }
        }
    ],
    yAxis: [
        {
            type: 'value'
        }
    ],
    series: [
        {
            name: '登录次数',
            type: 'bar',
            barWidth: '60%',
            data: [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
        }
    ]
});
sessionTimeChart.setOption({
    title: {
        text: '单次时长统计',
        subtext: '所选日期区间单次使用时长统计',
        x: 'center',
        show: false
    },
    grid: {
        left: '3%',
        right: '6%',
        bottom: '10%',
        containLabel: true
    },
    tooltip: {
        trigger: 'item',
        formatter: "{a} <br/>{b} : {c} ({d}%)"
    },
    legend: {
        orient: 'vertical',
        /*left: 'left',*/
        left: "300px",
        top: "100px",
        data: ['[0,60)秒', '[1,5)分', '[5,10)分', '[10,30)分', '30分以上']
    },
    series: [
        {
            name: '单次使用时长',
            type: 'pie',
            radius: '55%',
            center: ['50%', '60%'],
            data: [
                {"name": "[0,60)秒"},
                {"name": "[1,5)分"},
                {"name": "[5,10)分"},
                {"name": "[10,30)分"},
                {"name": "30分以上"}
            ],
            itemStyle: {
                emphasis: {
                    shadowBlur: 10,
                    shadowOffsetX: 0,
                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                }
            }
        }
    ]
});
// 当dom准备好后调用
$(function () {
    var operatorId = $('#operatorId').val();
    $('.operatorId2').css('display', 'none');
    var operatorId2Select = $('#' + operatorId);
    if (operatorId2Select.attr('title') != '0') {
        operatorId2Select.css('display', 'inline-block');
    }
    refreshEcharts();
    refreshToday();
});
// 下拉框绑定事件
$('#operatorId').change(function () {
    var operatorId = $('#operatorId').val();
    $('.operatorId').css('display', 'none');
    var operatorId2Select = $('#' + operatorId);
    if (operatorId2Select.attr('title') != '0') {
        operatorId2Select.css('display', 'inline-block');
    }
    refreshEcharts();
    refreshToday();
});
$('.operatorId2').change(function () {
    refreshEcharts();
    refreshToday();
});
