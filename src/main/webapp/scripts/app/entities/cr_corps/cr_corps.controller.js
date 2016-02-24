'use strict';

angular.module('craftApp')
    .controller('Cr_corpsController', function ($scope, $state, Cr_corps, Cr_corpsSearch) {

        $scope.cr_corpss = [];
        $scope.loadAll = function() {
            Cr_corps.query(function(result) {
               $scope.cr_corpss = result;
            });
        };
        $scope.loadAll();


        $scope.search = function () {
            Cr_corpsSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.cr_corpss = result;
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
            $scope.cr_corps = {
                co_nom_fr_fr: null,
                id: null
            };
        };
    });
