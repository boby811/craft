'use strict';

angular.module('craftApp')
    .controller('Cr_type_corpsController', function ($scope, $state, Cr_type_corps, Cr_type_corpsSearch) {

        $scope.cr_type_corpss = [];
        $scope.loadAll = function() {
            Cr_type_corps.query(function(result) {
               $scope.cr_type_corpss = result;
            });
        };
        $scope.loadAll();


        $scope.search = function () {
            Cr_type_corpsSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.cr_type_corpss = result;
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
            $scope.cr_type_corps = {
                tc_libelle_fr_fr: null,
                id: null
            };
        };
    });
