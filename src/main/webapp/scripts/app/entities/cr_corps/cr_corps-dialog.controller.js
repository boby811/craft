'use strict';

angular.module('craftApp').controller('Cr_corpsDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Cr_corps', 'Cr_corps_element', 'Cr_type_corps', 'Cr_systeme',
        function($scope, $stateParams, $uibModalInstance, entity, Cr_corps, Cr_corps_element, Cr_type_corps, Cr_systeme) {

        $scope.cr_corps = entity;
        $scope.cr_corps_elements = Cr_corps_element.query();
        $scope.cr_type_corpss = Cr_type_corps.query();
        $scope.cr_systemes = Cr_systeme.query();
        $scope.load = function(id) {
            Cr_corps.get({id : id}, function(result) {
                $scope.cr_corps = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('craftApp:cr_corpsUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.cr_corps.id != null) {
                Cr_corps.update($scope.cr_corps, onSaveSuccess, onSaveError);
            } else {
                Cr_corps.save($scope.cr_corps, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
