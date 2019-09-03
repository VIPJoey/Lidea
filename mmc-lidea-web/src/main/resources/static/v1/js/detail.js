function bindDataToLineChart(obj, setXAxisData, setYAxisData) {

    // 基于准备好的dom，初始化echarts实例
    let myChart = echarts.init(document.getElementById(obj.id));

    // 指定图表的配置项和数据
    let option = {
        title: {
            text: obj.text,
            subtext: obj.subText
        },
        tooltip: {
            trigger: 'axis'
        },
        legend: {
            data: ['']
        },
        toolbox: {
            show: true,
            feature: {
                mark: {show: true},
                dataView: {show: true, readOnly: false},
                magicType: {show: true, type: ['line', 'bar', 'stack', 'tiled']},
                restore: {show: true},
                saveAsImage: {show: true},
                dataZoom: {yAxisIndex: 'none'}
            }
        },
        dataZoom: [
            {
                show: false,
                start: 0,
                end: 100
            },
            {
                type: 'inside',
                start: 0,
                end: 100
            },
            {
                show: false,
                yAxisIndex: 0,
                filterMode: 'empty',
                width: 30,
                height: '80%',
                showDataShadow: false,
                left: '93%'
            }
        ],
        calculable: true,
        xAxis: [
            {
                type: 'category',
                boundaryGap: false,
                data: setXAxisData()
            }
        ],
        yAxis: [
            {
                type: 'value'
            }
        ],
        series: [
            {
                name: obj.subText,
                type: 'line',
                smooth: true,
                itemStyle: {normal: {areaStyle: {type: 'default'}}},
                data: setYAxisData()
            }
        ]
    };

    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);
    return myChart;
}

function bindDataToAccess(resp) {

    let obj = {};
    obj.id = 'divAccess';
    obj.text = resp.serviceName + "." + resp.methodName;
    obj.subText = "调用次数";

    let xAxis = [];
    let yAxis = [];

    // we need to revers it
    for (let i = resp.data.length - 1; i > 0; i--) {
        let d = resp.data[i];
        xAxis.push(d.time);
        yAxis.push(d.count);
    }
    bindDataToLineChart(obj, function () {
        return xAxis;
    }, function () {
        return yAxis;
    });

}

function bindDataToException(resp) {

    let obj = {};
    obj.id = 'divException';
    obj.text = resp.serviceName + "." + resp.methodName;
    obj.subText = "异常次数";

    let xAxis = [];
    let yAxis = [];

    // we need to revers it
    for (let i = resp.data.length - 1; i > 0; i--) {
        let d = resp.data[i];
        xAxis.push(d.time);
        yAxis.push(d.exception);
    }
    let mychart = bindDataToLineChart(obj, function () {
        return xAxis;
    }, function () {
        return yAxis;
    });

    bindEventToException(mychart, resp);

}

function bindEventToException(mychart, resp) {

    mychart.getZr().on('click', params => {
        const pointInPixel = [params.offsetX, params.offsetY];
        if (mychart.containPixel('grid', pointInPixel)) {
            let xIndex = mychart.convertFromPixel({seriesIndex: 0}, [params.offsetX, params.offsetY])[0];
            /*事件处理代码书写位置*/
            let op = mychart.getOption();

            let timeStr = op.xAxis[0].data[xIndex];
            let traceIds = resp.data[xIndex].traceIds;

            if (null == traceIds || "" === traceIds) {
                return
            }

            let time = new Date(timeStr).getTime();
            // let url = Nora.Util.StringUtil.format("/lidea/errorList?from={}&to={}&size={}", time, time, 100);
            // window.open(url, "_blank");
            $("#txtAppName").val(resp.appName);
            $("#txtServiceName").val(resp.serviceName);
            $("#txtMethodName").val(resp.methodName);
            $("#txtFrom").val(time);
            $("#txtTo").val(time);
            $("#txtSize").val(1000);
            $("#frmErrorList").submit();
        }

    });

}

function openErrorListPage() {
    
}

function bindDataToAvg(resp) {
    let obj = {};
    obj.id = 'divAvg';
    obj.text = resp.serviceName + "." + resp.methodName;
    obj.subText = "平均响应时间";

    let xAxis = [];
    let yAxis = [];

    // we need to revers it
    for (let i = resp.data.length - 1; i > 0; i--) {
        let d = resp.data[i];
        xAxis.push(d.time);
        yAxis.push(d.avg);
    }
    bindDataToLineChart(obj, function () {
        return xAxis;
    }, function () {
        return yAxis;
    });
}
