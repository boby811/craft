'use strict';

angular.module('craftApp')
	.controller('Cr_type_corpsDeleteController', function($scope, $uibModalInstance, entity, Cr_type_corps) {

        $scope.cr_type_corps = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Cr_type_corps.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
