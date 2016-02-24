'use strict';

angular.module('craftApp')
	.controller('Cr_elementDeleteController', function($scope, $uibModalInstance, entity, Cr_element) {

        $scope.cr_element = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Cr_element.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
