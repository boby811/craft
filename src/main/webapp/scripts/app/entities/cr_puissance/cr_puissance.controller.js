'use strict';

angular.module('craftApp')
    .controller('Cr_puissanceController', function ($scope, $state, Cr_puissance, Cr_puissanceSearch) {

        $scope.cr_puissances = [];
        $scope.loadAll = function() {
            Cr_puissance.query(function(result) {
               $scope.cr_puissances = result;
            });
        };
        $scope.loadAll();


        $scope.search = function () {
            Cr_puissanceSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.cr_puissances = result;
            }, function(response) {
                if(response.status === 404) {
                    $scope.loadAll();
                }
            });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.cr_puissance = {
                pu_libelle: null,
                id: null
            };
        };
    });
