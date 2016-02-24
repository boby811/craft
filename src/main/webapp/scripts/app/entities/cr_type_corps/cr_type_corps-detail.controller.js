'use strict';

angular.module('craftApp')
    .controller('Cr_type_corpsDetailController', function ($scope, $rootScope, $stateParams, entity, Cr_type_corps, Cr_corps) {
        $scope.cr_type_corps = entity;
        $scope.load = function (id) {
            Cr_type_corps.get({id: id}, function(result) {
                $scope.cr_type_corps = result;
            });
        };
        var unsubscribe = $rootScope.$on('craftApp:cr_type_corpsUpdate', function(event, result) {
            $scope.cr_type_corps = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
