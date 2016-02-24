'use strict';

angular.module('craftApp')
    .controller('Cr_imageController', function ($scope, $state, Cr_image, Cr_imageSearch) {

        $scope.cr_images = [];
        $scope.loadAll = function() {
            Cr_image.query(function(result) {
               $scope.cr_images = result;
            });
        };
        $scope.loadAll();


        $scope.search = function () {
            Cr_imageSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.cr_images = result;
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
            $scope.cr_image = {
                im_url: null,
                id: null
            };
        };
    });
