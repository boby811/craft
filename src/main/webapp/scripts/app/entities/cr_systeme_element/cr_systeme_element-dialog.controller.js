'use strict';

angular.module('craftApp').controller('Cr_systeme_elementDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Cr_systeme_element', 'Cr_element', 'Cr_systeme',
        function($scope, $stateParams, $uibModalInstance, entity, Cr_systeme_element, Cr_element, Cr_systeme) {

        $scope.cr_systeme_element = entity;
        $scope.cr_elements = Cr_element.query();
        $scope.cr_systemes = Cr_systeme.query();
        $scope.load = function(id) {
            Cr_systeme_element.get({id : id}, function(result) {
                $scope.cr_systeme_element = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('craftApp:cr_systeme_elementUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.cr_systeme_element.id != null) {
                Cr_systeme_element.update($scope.cr_systeme_element, onSaveSuccess, onSaveError);
            } else {
                Cr_systeme_element.save($scope.cr_systeme_element, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
