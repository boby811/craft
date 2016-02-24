'use strict';

angular.module('craftApp')
    .controller('Cr_categorie_craftDetailController', function ($scope, $rootScope, $stateParams, entity, Cr_categorie_craft, Cr_image, Cr_objet_craft) {
        $scope.cr_categorie_craft = entity;
        $scope.load = function (id) {
            Cr_categorie_craft.get({id: id}, function(result) {
                $scope.cr_categorie_craft = result;
            });
        };
        var unsubscribe = $rootScope.$on('craftApp:cr_categorie_craftUpdate', function(event, result) {
            $scope.cr_categorie_craft = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
