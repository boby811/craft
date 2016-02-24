'use strict';

angular.module('craftApp')
	.controller('Cr_objet_craftDeleteController', function($scope, $uibModalInstance, entity, Cr_objet_craft) {

        $scope.cr_objet_craft = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Cr_objet_craft.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
