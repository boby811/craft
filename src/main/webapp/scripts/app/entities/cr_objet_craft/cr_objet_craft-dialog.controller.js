'use strict';

angular.module('craftApp').controller('Cr_objet_craftDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Cr_objet_craft', 'Cr_puissance', 'Cr_categorie_craft', 'Cr_element',
        function($scope, $stateParams, $uibModalInstance, entity, Cr_objet_craft, Cr_puissance, Cr_categorie_craft, Cr_element) {

        $scope.cr_objet_craft = entity;
        $scope.cr_puissances = Cr_puissance.query();
        $scope.cr_categorie_crafts = Cr_categorie_craft.query();
        $scope.cr_elements = Cr_element.query();
        $scope.load = function(id) {
            Cr_objet_craft.get({id : id}, function(result) {
                $scope.cr_objet_craft = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('craftApp:cr_objet_craftUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.cr_objet_craft.id != null) {
                Cr_objet_craft.update($scope.cr_objet_craft, onSaveSuccess, onSaveError);
            } else {
                Cr_objet_craft.save($scope.cr_objet_craft, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
