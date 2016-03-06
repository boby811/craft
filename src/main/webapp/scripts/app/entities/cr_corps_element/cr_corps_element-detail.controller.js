'use strict';

angular.module('craftApp').controller('Cr_corps_elementDetailController',
    ['$scope', '$stateParams', '$state', 'entity', 'Cr_corps_element', 'Cr_corps', 'Cr_element','Cr_systeme',
        function($scope, $stateParams, $state, entity, Cr_corps_element, Cr_corps, Cr_element, Cr_systeme) {

        $scope.cr_corps_element = entity;
        $scope.cr_corpss = Cr_corps.query();
        $scope.cr_elements = Cr_element.query();
        
    	// saisie du nom du systeme
    	$scope.system = null;
    	$scope.cr_systemes = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.loadAll = function() {
            Cr_systeme.query({page: $scope.page - 1, size: 1000000, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                    $scope.cr_systemes = result;
            });
        };
        $scope.loadAll();
        $scope.load = function(id) {
            Cr_corps_element.get({id : id}, function(result) {
                $scope.cr_corps_element = result;
            });
        };

        $scope.systemeSelection = function() {
        	   console.log($scope.system);
        	};
        
        var onSaveSuccess = function (result) {
            alert("OnSaveSuccess");
            $state.go('home');
        	$scope.$emit('craftApp:cr_corps_elementUpdate', result);
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
}]);
