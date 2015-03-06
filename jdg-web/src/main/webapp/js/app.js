/**
 * Created by samuele on 2/25/15.
 */

var app = angular.module("nvd3TestApp", ['nvd3ChartDirectives']);

app.directive('extendedPieChart', function() {
    "use strict";
    return {
        restrict: 'E',
        require: '^nvd3PieChart',
        link: function ($scope, $element, $attributes, nvd3PieChart) {
            $scope.d3Call = function (data, chart) {
//                       return d3.select('#' + $scope.id + ' svg')
//                               .datum(data)
//                               .transition()
//                               .duration(500)
//                               .call(chart);
                var svg = d3.select('#' + $scope.id + ' svg')
                    .datum(data);
                var path = svg.selectAll('path');
                path.data(data)
                    .transition()
                    .ease("linear")
                    .duration(500)
                return svg.transition()
                    .duration(500)
                    .call(chart);
            }
        }
    }
});


function MainCtrl($scope, $http, $timeout, $interval) {

    $scope.loadPrimary = function() {
        $http.get("/rest/jdg/distprimary").success(
            function (data, status, headers, config) {
                $scope.primaryData = data.servers
            }).
            error(function (data, status, headers, config) {
                console.log("ERROR: " + status)
            });
    }

    $scope.loadall = function() {
        $http.get("/rest/jdg/distall").success(
            function(data, status, headers, config) {
                console.log(data)
                $scope.allData = data.servers
            }).
            error(function(data, status, headers, config) {
                console.log("ERROR: "+status)
            });
    }

    $scope.items = [{
        key: "",
        value: ""
    }];

    $scope.add = function () {
        $scope.items.push({
            key: "",
            value: ""
        });
    };

    $scope.put = function() {
        console.log($scope.items)
        $http.post("/rest/jdg/put",$scope.items).success(
            function(data, status, headers, config) {
                console.log(data)
            }).
            error(function(data, status, headers, config) {
                console.log("ERROR: "+status)
            });


        $timeout( function(){ $scope.loadPrimary() },1000)
        $timeout( function(){ $scope.loadall() },1000)
        $scope.items = [{
            key: "",
            value: ""
        }];

    }

    $scope.xFunction = function () {
        return function (d) {
            return d.serverName;
        };
    }

    $scope.yFunction = function () {
        return function (d) {
            return d.numberOfEntries;
        };
    }

    $scope.startTw = function() {
        $http.post("/rest/jdg/starttw",$scope.hashtag).success(
            function(data, status, headers, config) {
                console.log(data)
            }).
            error(function(data, status, headers, config) {
                console.log("ERROR: "+status)
            });
        $scope.refreshPriPromise = $interval( function() { $scope.loadPrimary() }, 2000)
        $scope.refreshAllPromise = $interval( function() { $scope.loadall() }, 2000)
    }

    $scope.stopTw = function() {
        $http.get("/rest/jdg/stoptw").success(
            function(data, status, headers, config) {
                console.log(data)
            }).
            error(function(data, status, headers, config) {
                console.log("ERROR: "+status)
            });
        $interval.cancel($scope.refreshAllPromise)
        $interval.cancel($scope.refreshPriPromise)
    }

    $scope.loadPrimary()
    $scope.loadall()
}
