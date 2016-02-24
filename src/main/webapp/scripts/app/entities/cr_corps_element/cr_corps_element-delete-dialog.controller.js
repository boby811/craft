'use strict';

angular.module('craftApp')
	.controller('Cr_corps_elementDeleteController', function($scope, $uibModalInstance, entity, Cr_corps_element) {

        $scope.cr_corps_element = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Cr_corps_element.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
