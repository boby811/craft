'use strict';

angular.module('craftApp')
    .controller('Cr_systemeDetailController', function ($scope, $rootScope, $stateParams, entity, Cr_systeme, Cr_systeme_element, Cr_corps) {
        $scope.cr_systeme = entity;
        $scope.load = function (id) {
            Cr_systeme.get({id: id}, function(result) {
                $scope.cr_systeme = result;
            });
        };
        var unsubscribe = $rootScope.$on('craftApp:cr_systemeUpdate', function(event, result) {
            $scope.cr_systeme = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
