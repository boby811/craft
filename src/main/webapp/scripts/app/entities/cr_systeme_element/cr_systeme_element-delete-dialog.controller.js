'use strict';

angular.module('craftApp')
	.controller('Cr_systeme_elementDeleteController', function($scope, $uibModalInstance, entity, Cr_systeme_element) {

        $scope.cr_systeme_element = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Cr_systeme_element.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
