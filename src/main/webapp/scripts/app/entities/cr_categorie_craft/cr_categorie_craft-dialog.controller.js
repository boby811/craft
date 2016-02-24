'use strict';

angular.module('craftApp').controller('Cr_categorie_craftDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Cr_categorie_craft', 'Cr_image', 'Cr_objet_craft',
        function($scope, $stateParams, $uibModalInstance, entity, Cr_categorie_craft, Cr_image, Cr_objet_craft) {

        $scope.cr_categorie_craft = entity;
        $scope.cr_images = Cr_image.query();
        $scope.cr_objet_crafts = Cr_objet_craft.query();
        $scope.load = function(id) {
            Cr_categorie_craft.get({id : id}, function(result) {
                $scope.cr_categorie_craft = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('craftApp:cr_categorie_craftUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.cr_categorie_craft.id != null) {
                Cr_categorie_craft.update($scope.cr_categorie_craft, onSaveSuccess, onSaveError);
            } else {
                Cr_categorie_craft.save($scope.cr_categorie_craft, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
