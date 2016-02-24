'use strict';

angular.module('craftApp').controller('Cr_corps_elementDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Cr_corps_element', 'Cr_corps', 'Cr_element','Cr_systeme',
        function($scope, $stateParams, $uibModalInstance, entity, Cr_corps_element, Cr_corps, Cr_element, Cr_systeme) {

        $scope.cr_corps_element = entity;
        $scope.cr_corpss = Cr_corps.query();
        $scope.cr_elements = Cr_element.query();
        console.log($scope.cr_elements);
        $scope.cr_systemes = Cr_systeme.query();
        console.log($scope.cr_systemes);
        $scope.load = function(id) {
            Cr_corps_element.get({id : id}, function(result) {
                $scope.cr_corps_element = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('craftApp:cr_corps_elementUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.cr_corps_element.id != null) {
                Cr_corps_element.update($scope.cr_corps_element, onSaveSuccess, onSaveError);
            } else {
                Cr_corps_element.save($scope.cr_corps_element, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
