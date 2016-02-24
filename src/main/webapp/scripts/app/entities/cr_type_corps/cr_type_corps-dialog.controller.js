'use strict';

angular.module('craftApp').controller('Cr_type_corpsDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Cr_type_corps', 'Cr_corps',
        function($scope, $stateParams, $uibModalInstance, entity, Cr_type_corps, Cr_corps) {

        $scope.cr_type_corps = entity;
        $scope.cr_corpss = Cr_corps.query();
        $scope.load = function(id) {
            Cr_type_corps.get({id : id}, function(result) {
                $scope.cr_type_corps = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('craftApp:cr_type_corpsUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.cr_type_corps.id != null) {
                Cr_type_corps.update($scope.cr_type_corps, onSaveSuccess, onSaveError);
            } else {
                Cr_type_corps.save($scope.cr_type_corps, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
