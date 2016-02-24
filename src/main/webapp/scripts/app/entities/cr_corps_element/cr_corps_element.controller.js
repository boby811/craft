'use strict';

angular.module('craftApp')
    .controller('Cr_corps_elementController', function ($scope, $state, Cr_corps_element, Cr_corps_elementSearch, ParseLinks) {

        $scope.cr_corps_elements = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.loadAll = function() {
            Cr_corps_element.query({page: $scope.page - 1, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.cr_corps_elements = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            Cr_corps_elementSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.cr_corps_elements = result;
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
            $scope.cr_corps_element = {
                ce_quantite: null,
                id: null
            };
        };
    });
