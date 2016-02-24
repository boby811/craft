'use strict';

angular.module('craftApp')
    .controller('Cr_puissanceDetailController', function ($scope, $rootScope, $stateParams, entity, Cr_puissance, Cr_objet_craft) {
        $scope.cr_puissance = entity;
        $scope.load = function (id) {
            Cr_puissance.get({id: id}, function(result) {
                $scope.cr_puissance = result;
            });
        };
        var unsubscribe = $rootScope.$on('craftApp:cr_puissanceUpdate', function(event, result) {
            $scope.cr_puissance = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
