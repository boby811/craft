'use strict';

angular.module('craftApp')
    .controller('Cr_elementController', function ($scope, $state, Cr_element, Cr_elementSearch) {

        $scope.cr_elements = [];
        $scope.loadAll = function() {
            Cr_element.query(function(result) {
               $scope.cr_elements = result;
            });
        };
        $scope.loadAll();


        $scope.search = function () {
            Cr_elementSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.cr_elements = result;
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
            $scope.cr_element = {
                el_nom_court_fr_fr: null,
                el_nom_long_fr_fr: null,
                el_description_fr_fr: null,
                el_num_atomique: null,
                el_point_fusion: null,
                id: null
            };
        };
    });
