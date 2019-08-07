<!-- chart.jsp-->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <script type="text/javascript">
            window.onload = function() {

                var dataPoints = [[],[]];
                var chart = new CanvasJS.Chart("chartContainer", {
                    zoomEnabled: true,
                    theme: "light2",
                    title: {
                        text: "Log Counts"
                    },
                    axisX: {
                        title: "Time",
                        interval: 30,
                        intervalType: "second"
                    },
                    axisY:{
                        title: "Count",
                        includeZero: true
                    },
                    toolTip: {
                        shared: true
                    },
                    legend: {
                        cursor:"pointer",
                        verticalAlign: "top",
                        fontSize: 20,
                        fontColor: "dimGrey",
                    },
                    data: []
                });



                chart.render();

                var updateInterval = 1500;

                var time = new Date;

                var index = 0;

                var city_indexes = {};
                function updateChart(count) {
                    time.setTime(time.getTime()+ updateInterval);
                    var xmlHttp = new XMLHttpRequest();
                    xmlHttp.open("GET", "/datapoints", false); // false for synchronous request
                    xmlHttp.send(null);
                    var response = JSON.parse(xmlHttp.responseText);
                    var cities = Object.keys(response);
                    if (cities.length !== 0) {
                        for (var i = 0; i < cities.length; i++) {
                            var flag = false;
                            console.log(chart.options.data.length)
                            for (var j = 0; j < chart.options.data.length; j++) {
                                if (chart.options.data[j].name === cities[i]) {
                                    flag = true;
                                    break;
                                }
                            }
                            if (flag === false) {
                                chart.options.data.push({
                                    type: "line",
                                    xValueType: "dateTime",
                                    yValueFormatString: "#",
                                    showInLegend: true,
                                    name: cities[i],
                                    dataPoints: dataPoints[index]
                                });
                                city_indexes[cities[i]] = index;
                                dataPoints.push([]);
                                index++;
                            } else {
                                dataPoints[city_indexes[cities[i]]].push({
                                    x: time.getTime(),
                                    y: response[cities[i]]
                                })
                            }
                            chart.render();
                        }
                    }
                }
                setInterval(function(){updateChart()}, updateInterval);

                }

    </script>
</head>
<body>
<div id="chartContainer" style="height: 370px; width: 100%;"></div>
<script src="https://canvasjs.com/assets/script/canvasjs.min.js"></script>

</body>
</html>