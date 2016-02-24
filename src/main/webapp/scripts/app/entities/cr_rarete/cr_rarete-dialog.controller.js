'use strict';

angular.module('craftApp').controller('Cr_rareteDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Cr_rarete', 'Cr_element',
        function($scope, $stateParams, $uibModalInstance, entity, Cr_rarete, Cr_element) {

        $scope.cr_rarete = entity;
        $scope.cr_elements = Cr_element.query();
        $scope.load = function(id) {
            Cr_rarete.get({id : id}, function(result) {
                $scope.cr_rarete = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('craftApp:cr_rareteUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.cr_rarete.id != null) {
                Cr_rarete.update($scope.cr_rarete, onSaveSuccess, onSaveError);
            } else {
                Cr_rarete.save($scope.cr_rarete, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
