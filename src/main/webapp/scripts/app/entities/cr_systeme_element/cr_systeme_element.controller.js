'use strict';

angular.module('craftApp')
    .controller('Cr_systeme_elementController', function ($scope, $state, Cr_systeme_element, Cr_systeme_elementSearch) {

        $scope.cr_systeme_elements = [];
        $scope.loadAll = function() {
            Cr_systeme_element.query(function(result) {
               $scope.cr_systeme_elements = result;
            });
        };
        $scope.loadAll();


        $scope.search = function () {
            Cr_systeme_elementSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.cr_systeme_elements = result;
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
            $scope.cr_systeme_element = {
                se_quantite: null,
                id: null
            };
        };
    });
