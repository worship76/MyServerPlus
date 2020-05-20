<%--
  Created by IntelliJ IDEA.
  User: 话长更
  Date: 2020/4/14
  Time: 11:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script src="https://code.highcharts.com.cn/highcharts/highcharts.js"></script>
    <script src="https://code.highcharts.com.cn/highcharts/modules/exporting.js"></script>
    <script src="https://code.highcharts.com.cn/highcharts/modules/oldie.js"></script>
    <script src="https://code.highcharts.com.cn/highcharts-plugins/highcharts-zh_CN.js"></script>
    <script src="https://code.highcharts.com.cn/highcharts/themes/grid-light.js"></script>
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/jquery-easyui-1.3.3/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/jquery-easyui-1.3.3/themes/icon.css">
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/jquery-easyui-1.3.3/jquery.min.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/jquery-easyui-1.3.3/jquery.easyui.min.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/jquery-easyui-1.3.3/locale/easyui-lang-zh_CN.js"></script>
    <script>
        (function(a)
        {"object"===typeof module&&module.exports?(a["default"]=a,module.exports=a):
            "function"===typeof
                define&&define.amd?define("highcharts/themes/grid-light",["highcharts"], function(b) {a(b);a.Highcharts=b;return a}):
                a("undefined"!==typeof Highcharts?Highcharts:void 0)
        })
        (function(a)
        {function b(a,b,c,d){a.hasOwnProperty(b)||(a[b]=d.apply(null,c))}a=a?a._modules:{};
            b(a,"themes/grid-light.js",[a["parts/Globals.js"]],
                function(a){a.createElement("link",{href:"https://fonts.googleapis.com/css?family=Dosis:400,600", rel:"stylesheet",type:"text/css"},
                    null,document.getElementsByTagName("head")[0]);
                    a.theme={colors:"#7cb5ec #f7a35c #90ee7e #7798BF #aaeeee #ff0066 #eeaaee #55BF3B #DF5353 #7798BF #aaeeee".split(" "),
                        chart:{backgroundColor:null,
                            style:{fontFamily:"Dosis, sans-serif"}},
                        title:{style:{fontSize:"16px",
                                fontWeight:"bold",textTransform:"uppercase"}},
                        tooltip:{borderWidth:0,
                            backgroundColor:"rgba(219,219,216,0.8)",shadow:!1},
                        legend:{backgroundColor:"#F0F0EA",
                            itemStyle:{fontWeight:"bold",fontSize:"13px"}},
                        xAxis:{gridLineWidth:1, labels:{style:{fontSize:"12px"}}},
                        yAxis:{minorTickInterval:"auto",
                            title:{style:{textTransform:"uppercase"}},
                            labels:{style:{fontSize:"12px"}}},
                        plotOptions:{candlestick:{lineColor:"#404048"}}};
                    a.setOptions(a.theme)});b(a,"masters/themes/grid-light.src.js",[],function(){})});
    </script>
</head>
<body>
    <table title="传感器(图)" class="easyui-datagrid" fitColumns="true"
           pagination="true" rownumbers="true"
           fit="true">
        <div id="temperature" style="min-width:400px;height:400px"></div>
        <script>
            var chart = Highcharts.chart('temperature', {
                chart: {
                    type: 'line'
                },
                title: {
                    text: '温度示意图'
                },
                subtitle: {
                    text: '数据来源: www.weather.com'
                },
                xAxis: {
                    categories: ['4/01', '4/02', '4/03', '4/04', '4/05', '4/06', '4/07', '4/08']
                },
                yAxis: {
                    title: {
                        text: '温度 (°C)'
                    }
                },
                plotOptions: {
                    line: {
                        dataLabels: {
                            // 开启数据标签
                            enabled: true
                        },
                        // 鼠标跟踪，对应的提示框、点击事件会失效
                        enableMouseTracking: true
                    }
                },
                series: [{
                    name: '传感器1',
                    data: [33.4, 33.7, 34.2, 32.5, 33.0, 31.5, 35.2,37.6]
                }, {
                    name: '传感器2',
                    data: [ 33.7, 37.6, 33.4,32.5,31.5, 33.0, 34.2, 35.2]
                }]
            });

        </script>

        <hr/>

        <div id="concentration" style="min-width:400px;height:400px"></div>
        <script>
            var chart = Highcharts.chart('concentration', {
                chart: {
                    type: 'line'
                },
                title: {
                    text: '浓度示意图'
                },
                xAxis: {
                    categories: ['4/01', '4/02', '4/03', '4/04', '4/05', '4/06', '4/07', '4/08']
                },
                yAxis: {
                    title: {
                        text: '浓度 (C)'
                    }
                },
                plotOptions: {
                    line: {
                        dataLabels: {
                            // 开启数据标签
                            enabled: true
                        },
                        // 鼠标跟踪，对应的提示框、点击事件会失效
                        enableMouseTracking: true
                    }
                },
                series: [{
                    name: '传感器1',
                    data: [7.0, 6.9, 9.5, 14.5, 18.4, 21.5, 25.2,27.6]
                }, {
                    name: '传感器2',
                    data: [3.9, 4.2, 5.7, 8.5, 11.9, 15.2, 17.0, 16.6]
                }]
            });

        </script>
    </table>

</body>
</html>
