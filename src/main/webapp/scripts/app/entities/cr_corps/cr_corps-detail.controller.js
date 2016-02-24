'use strict';

angular.module('craftApp')
    .controller('Cr_corpsDetailController', function ($scope, $rootScope, $stateParams, entity, Cr_corps, Cr_corps_element, Cr_type_corps, Cr_systeme) {
        $scope.cr_corps = entity;
        $scope.load = function (id) {
            Cr_corps.get({id: id}, function(result) {
                $scope.cr_corps = result;
            });
        };
        var unsubscribe = $rootScope.$on('craftApp:cr_corpsUpdate', function(event, result) {
            $scope.cr_corps = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
