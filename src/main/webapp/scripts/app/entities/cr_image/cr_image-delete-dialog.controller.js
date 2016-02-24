'use strict';

angular.module('craftApp')
	.controller('Cr_imageDeleteController', function($scope, $uibModalInstance, entity, Cr_image) {

        $scope.cr_image = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Cr_image.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
