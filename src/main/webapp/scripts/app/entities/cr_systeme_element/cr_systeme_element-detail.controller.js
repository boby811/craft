'use strict';

angular.module('craftApp')
    .controller('Cr_systeme_elementDetailController', function ($scope, $rootScope, $stateParams, entity, Cr_systeme_element, Cr_element, Cr_systeme) {
        $scope.cr_systeme_element = entity;
        $scope.load = function (id) {
            Cr_systeme_element.get({id: id}, function(result) {
                $scope.cr_systeme_element = result;
            });
        };
        var unsubscribe = $rootScope.$on('craftApp:cr_systeme_elementUpdate', function(event, result) {
            $scope.cr_systeme_element = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
