'use strict';

angular.module('craftApp')
    .controller('Cr_rareteController', function ($scope, $state, Cr_rarete, Cr_rareteSearch) {

        $scope.cr_raretes = [];
        $scope.loadAll = function() {
            Cr_rarete.query(function(result) {
               $scope.cr_raretes = result;
            });
        };
        $scope.loadAll();


        $scope.search = function () {
            Cr_rareteSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.cr_raretes = result;
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
            $scope.cr_rarete = {
                ra_libelle_fr_fr: null,
                id: null
            };
        };
    });
