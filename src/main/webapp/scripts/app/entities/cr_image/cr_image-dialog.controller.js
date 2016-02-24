'use strict';

angular.module('craftApp').controller('Cr_imageDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Cr_image', 'Cr_element', 'Cr_categorie_craft',
        function($scope, $stateParams, $uibModalInstance, entity, Cr_image, Cr_element, Cr_categorie_craft) {

        $scope.cr_image = entity;
        $scope.cr_elements = Cr_element.query();
        $scope.cr_categorie_crafts = Cr_categorie_craft.query();
        $scope.load = function(id) {
            Cr_image.get({id : id}, function(result) {
                $scope.cr_image = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('craftApp:cr_imageUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.cr_image.id != null) {
                Cr_image.update($scope.cr_image, onSaveSuccess, onSaveError);
            } else {
                Cr_image.save($scope.cr_image, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
