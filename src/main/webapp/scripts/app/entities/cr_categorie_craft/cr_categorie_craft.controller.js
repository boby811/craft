'use strict';

angular.module('craftApp')
    .controller('Cr_categorie_craftController', function ($scope, $state, Cr_categorie_craft, Cr_categorie_craftSearch) {

        $scope.cr_categorie_crafts = [];
        $scope.loadAll = function() {
            Cr_categorie_craft.query(function(result) {
               $scope.cr_categorie_crafts = result;
            });
        };
        $scope.loadAll();


        $scope.search = function () {
            Cr_categorie_craftSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.cr_categorie_crafts = result;
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
            $scope.cr_categorie_craft = {
                cc_nom_court_fr_fr: null,
                cc_nom_long_fr_fr: null,
                id: null
            };
        };
    });
