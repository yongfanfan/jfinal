// 截断年份函数
function convertDate(item) {
    return item.substring(5);
}
// 异步加载图表数据
function refreshEcharts() {
    var operatorId = getOperatorId();
    var start_day = $('#start_day').val();
    var end_day = $('#end_day').val();
    loadOpen(0);
    $.get('admin/home/statData', {
        operatorId: operatorId,
        start: start_day,
        end: end_day
    }).done(function (data) {
        loadClose(0);
        // 填入数据
        userChart.setOption({
            xAxis: {
                data: data.date.map(convertDate)
            },
            series: data.user
        });
        browseChart.setOption({
            xAxis: {
                data: data.date.map(convertDate)
            },
            series: data.browse
        });
        loginTimeChart.setOption({
            series: {
                data: data.loginTime
            }
        });
        sessionTimeChart.setOption({
            series: [
                {
                    data: data.sessionTime
                }
            ]
        });
        var totalUserChart = echarts.init($('#totalUser')[0]);
        totalUserChart.setOption({
            title: {
                text: '用户总数统计',
                x: 'center',
                show: false
            },
            tooltip: {
                trigger: 'axis'
            },
            legend: {
                data: data.totalUsers.legendData,
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
                data: data.totalUsers.xAxisData
            },
            yAxis: {
                name: '个数',
                type: 'value'
            },
            series: data.totalUsers.series
        });
    });
    loadOpen(3);
    $.get('admin/home/getHaiTaiTotalUsers', {
        operatorId: operatorId,
        start: start_day,
        end: end_day
    }).done(function (data) {
        loadClose(3);
        if (data.status !== 1) {
            return;
        }
        var htUserChart = echarts.init($('#htUser')[0]);
        htUserChart.setOption({
            title: {
                text: '海苔登录用户总数统计',
                x: 'center',
                show: false
            },
            tooltip: {
                trigger: 'axis'
            },
            legend: {
                data: data.legendData,
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
                data: data.xAxisData
            },
            yAxis: {
                name: '个数',
                type: 'value'
            },
            series: data.series
        });
    });
}
// 异步加载今日数据
function refreshToday() {
    var operatorId = getOperatorId();
    loadOpen(1);
    loadOpen(2);
    $.get('admin/home/basicInfo', {operatorId: operatorId}).done(function (data) {
        loadClose(1);
        $.each(data, function (trName, node) {
            $.each(node, function (tdName, value) {
                var selector = '#' + trName + ' > [name=' + tdName + ']';
                $(selector).html(value);
            })
        });
    });
    $('#ajaxTabel03').load('admin/home/hotRank', {operatorId: operatorId}, function () {
        loadClose(2);
    });
}
// 获取选择的运营商id
function getOperatorId() {
    var operatorId = $('#operatorId').val();
    var operatorId2 = $('#' + operatorId).val();
    if (operatorId2 != null) {
        return operatorId2;
    } else {
        return operatorId;
    }
}
// 报表下载功能
function exportUserCount() {
    var operatorId = getOperatorId();
    var start_day = $('#start_day').val();
    var end_day = $('#end_day').val();
    $('#downLoadIframe')[0].src = 'admin/home/exportUserCount?operatorId=' + operatorId + '&start=' + start_day
        + '&end=' + end_day;
}
function exportHeat() {
    var operatorId = getOperatorId();
    var start_day = $('#start_day').val();
    var end_day = $('#end_day').val();
    $('#downLoadIframe')[0].src = 'admin/home/exportHeat?operatorId=' + operatorId + '&start=' + start_day
        + '&end=' + end_day;
}
// 重新统计功能
function reStat() {
    var start_day = $('#start_day').val();
    var end_day = $('#end_day').val();
    if (!confirm("即将对所选时间区间重新进行统计（这可能会耗费很多服务器资源，请尽量选择用户不活跃时进行），是否确定？")) {
        return;
    }
    $('#reStatModal').modal('show');
    $.get('admin/datastatistics/reAnalysis', {start: start_day, end: end_day}).done(function (data) {
        alert(data.message);
        $('#reStatModal').modal('hide');
        refreshEcharts();
    });
}
// 在加载数据时弹出模态框
function loadOpen(i) {
    loadSwitch[i] = 1;
    $('#loadModal').modal('show');
}
function loadClose(i) {
    loadSwitch[i] = 0;
    if (loadSwitch.toString() == [0, 0, 0, 0].toString()) {
        $('#loadModal').modal('hide');
    }
}