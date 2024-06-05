/*
    Ceci est une exemple de comment prendre le donnee depuis le front
    Mila amboarina louni ny web.xml

    atao otrzao fa efa nataoko fa afaka modifier na raha misy tsy mety

    <?xml version="1.0" encoding="UTF-8"?>
    <web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
            xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
            xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee
                                http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd"
            version="4.0">

        <display-name>Voky App</display-name>

        <servlet>
            <servlet-name>StockIngredientServlet</servlet-name>
            <servlet-class>vokye.controller.StockIngredientServlet</servlet-class>
        </servlet>
        <servlet-mapping>
            <servlet-name>StockIngredientServlet</servlet-name>
            <url-pattern>/api/*</url-pattern>
        </servlet-mapping>
    </web-app>


*/


var app = angular.module('vokyApp', []);

// controleur généralisé mi-load avy any @ servlet
app.controller('MainController', function($scope, $http) {
    $scope.tableData = [];

    $scope.loadData = function(endpoint) {
        $http.get('/vokye/api/' + endpoint)
            .then(function(response) {
                $scope.tableData = response.data;
                console.log(response.data);
            }, function(error) {
                console.error('Error loading data:', error);
            });
    };


    // Load the categories, rallies, and speciales on page load
    $scope.loadData("vStockIngredient");
});


