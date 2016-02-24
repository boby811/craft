'use strict';

angular.module('craftApp')
    .controller('Cr_elementDetailController', function ($scope, $rootScope, $stateParams, entity, Cr_element, Cr_rarete, Cr_image, Cr_corps_element, Cr_objet_craft, Cr_systeme_element) {
        $scope.cr_element = entity;
        $scope.load = function (id) {
            Cr_element.get({id: id}, function(result) {
                $scope.cr_element = result;
            });
        };
        var unsubscribe = $rootScope.$on('craftApp:cr_elementUpdate', function(event, result) {
            $scope.cr_element = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
