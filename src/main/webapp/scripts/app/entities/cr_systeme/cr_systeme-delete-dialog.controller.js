'use strict';

angular.module('craftApp')
	.controller('Cr_systemeDeleteController', function($scope, $uibModalInstance, entity, Cr_systeme) {

        $scope.cr_systeme = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Cr_systeme.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
