'use strict';

angular.module('craftApp').controller('Cr_puissanceDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Cr_puissance', 'Cr_objet_craft',
        function($scope, $stateParams, $uibModalInstance, entity, Cr_puissance, Cr_objet_craft) {

        $scope.cr_puissance = entity;
        $scope.cr_objet_crafts = Cr_objet_craft.query();
        $scope.load = function(id) {
            Cr_puissance.get({id : id}, function(result) {
                $scope.cr_puissance = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('craftApp:cr_puissanceUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.cr_puissance.id != null) {
                Cr_puissance.update($scope.cr_puissance, onSaveSuccess, onSaveError);
            } else {
                Cr_puissance.save($scope.cr_puissance, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
