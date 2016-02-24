'use strict';

angular.module('craftApp')
    .controller('Cr_rareteDetailController', function ($scope, $rootScope, $stateParams, entity, Cr_rarete, Cr_element) {
        $scope.cr_rarete = entity;
        $scope.load = function (id) {
            Cr_rarete.get({id: id}, function(result) {
                $scope.cr_rarete = result;
            });
        };
        var unsubscribe = $rootScope.$on('craftApp:cr_rareteUpdate', function(event, result) {
            $scope.cr_rarete = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
