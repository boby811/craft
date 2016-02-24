'use strict';

angular.module('craftApp')
	.controller('Cr_corpsDeleteController', function($scope, $uibModalInstance, entity, Cr_corps) {

        $scope.cr_corps = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Cr_corps.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
