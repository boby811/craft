'use strict';

angular.module('craftApp')
    .controller('Cr_objet_craftController', function ($scope, $state, Cr_objet_craft, Cr_objet_craftSearch) {

        $scope.cr_objet_crafts = [];
        $scope.loadAll = function() {
            Cr_objet_craft.query(function(result) {
               $scope.cr_objet_crafts = result;
            });
        };
        $scope.loadAll();


        $scope.search = function () {
            Cr_objet_craftSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.cr_objet_crafts = result;
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
            $scope.cr_objet_craft = {
                oc_quantite_element: null,
                id: null
            };
        };
    });
