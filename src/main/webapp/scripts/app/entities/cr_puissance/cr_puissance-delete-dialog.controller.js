'use strict';

angular.module('craftApp')
	.controller('Cr_puissanceDeleteController', function($scope, $uibModalInstance, entity, Cr_puissance) {

        $scope.cr_puissance = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Cr_puissance.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
