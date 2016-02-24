'use strict';

angular.module('craftApp')
    .controller('Cr_imageDetailController', function ($scope, $rootScope, $stateParams, entity, Cr_image, Cr_element, Cr_categorie_craft) {
        $scope.cr_image = entity;
        $scope.load = function (id) {
            Cr_image.get({id: id}, function(result) {
                $scope.cr_image = result;
            });
        };
        var unsubscribe = $rootScope.$on('craftApp:cr_imageUpdate', function(event, result) {
            $scope.cr_image = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
