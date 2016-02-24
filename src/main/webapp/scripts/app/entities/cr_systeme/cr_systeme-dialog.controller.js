'use strict';

angular.module('craftApp').controller('Cr_systemeDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Cr_systeme', 'Cr_systeme_element', 'Cr_corps',
        function($scope, $stateParams, $uibModalInstance, entity, Cr_systeme, Cr_systeme_element, Cr_corps) {

        $scope.cr_systeme = entity;
        $scope.cr_systeme_elements = Cr_systeme_element.query();
        $scope.cr_corpss = Cr_corps.query();
        $scope.load = function(id) {
            Cr_systeme.get({id : id}, function(result) {
                $scope.cr_systeme = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('craftApp:cr_systemeUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.cr_systeme.id != null) {
                Cr_systeme.update($scope.cr_systeme, onSaveSuccess, onSaveError);
            } else {
                Cr_systeme.save($scope.cr_systeme, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
