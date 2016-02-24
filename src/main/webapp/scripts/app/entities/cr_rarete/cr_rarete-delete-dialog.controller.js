'use strict';

angular.module('craftApp')
	.controller('Cr_rareteDeleteController', function($scope, $uibModalInstance, entity, Cr_rarete) {

        $scope.cr_rarete = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Cr_rarete.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
