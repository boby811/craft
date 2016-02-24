'use strict';

angular.module('craftApp')
	.controller('Cr_categorie_craftDeleteController', function($scope, $uibModalInstance, entity, Cr_categorie_craft) {

        $scope.cr_categorie_craft = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Cr_categorie_craft.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
