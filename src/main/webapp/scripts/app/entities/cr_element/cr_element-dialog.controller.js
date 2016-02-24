'use strict';

angular.module('craftApp').controller('Cr_elementDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Cr_element', 'Cr_rarete', 'Cr_image', 'Cr_corps_element', 'Cr_objet_craft', 'Cr_systeme_element',
        function($scope, $stateParams, $uibModalInstance, entity, Cr_element, Cr_rarete, Cr_image, Cr_corps_element, Cr_objet_craft, Cr_systeme_element) {

        $scope.cr_element = entity;
        $scope.cr_raretes = Cr_rarete.query();
        $scope.cr_images = Cr_image.query();
        $scope.cr_corps_elements = Cr_corps_element.query();
        $scope.cr_objet_crafts = Cr_objet_craft.query();
        $scope.cr_systeme_elements = Cr_systeme_element.query();
        $scope.load = function(id) {
            Cr_element.get({id : id}, function(result) {
                $scope.cr_element = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('craftApp:cr_elementUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.cr_element.id != null) {
                Cr_element.update($scope.cr_element, onSaveSuccess, onSaveError);
            } else {
                Cr_element.save($scope.cr_element, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
