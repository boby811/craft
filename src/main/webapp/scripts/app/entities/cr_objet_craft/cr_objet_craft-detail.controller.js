'use strict';

angular.module('craftApp')
    .controller('Cr_objet_craftDetailController', function ($scope, $rootScope, $stateParams, entity, Cr_objet_craft, Cr_puissance, Cr_categorie_craft, Cr_element) {
        $scope.cr_objet_craft = entity;
        $scope.load = function (id) {
            Cr_objet_craft.get({id: id}, function(result) {
                $scope.cr_objet_craft = result;
            });
        };
        var unsubscribe = $rootScope.$on('craftApp:cr_objet_craftUpdate', function(event, result) {
            $scope.cr_objet_craft = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
