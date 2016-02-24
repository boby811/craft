'use strict';

angular.module('craftApp')
    .controller('Cr_corps_elementDetailController', function ($scope, $rootScope, $stateParams, entity, Cr_corps_element, Cr_corps, Cr_element, Cr_systeme) {
        $scope.cr_corps_element = entity;
        $scope.cr_systemes = Cr_systeme.query();
        console.log($scope.cr_systemes);
        $scope.load = function (id) {
            Cr_corps_element.get({id: id}, function(result) {
                $scope.cr_corps_element = result;
            });
        };
        var unsubscribe = $rootScope.$on('craftApp:cr_corps_elementUpdate', function(event, result) {
            $scope.cr_corps_element = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
