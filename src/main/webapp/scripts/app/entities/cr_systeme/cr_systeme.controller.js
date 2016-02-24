'use strict';

angular.module('craftApp')
    .controller('Cr_systemeController', function ($scope, $state, Cr_systeme, Cr_systemeSearch, ParseLinks) {

        $scope.cr_systemes = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.loadAll = function() {
            Cr_systeme.query({page: $scope.page - 1, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.cr_systemes = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
        	console.log($scope.searchQuery);
            Cr_systemeSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.cr_systemes = result;
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
            $scope.cr_systeme = {
                sy_nom_fr_fr: null,
                id: null
            };
        };
    });
