'use strict';

describe('Controller Tests', function() {

    describe('Cr_objet_craft Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockCr_objet_craft, MockCr_puissance, MockCr_categorie_craft, MockCr_element;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockCr_objet_craft = jasmine.createSpy('MockCr_objet_craft');
            MockCr_puissance = jasmine.createSpy('MockCr_puissance');
            MockCr_categorie_craft = jasmine.createSpy('MockCr_categorie_craft');
            MockCr_element = jasmine.createSpy('MockCr_element');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Cr_objet_craft': MockCr_objet_craft,
                'Cr_puissance': MockCr_puissance,
                'Cr_categorie_craft': MockCr_categorie_craft,
                'Cr_element': MockCr_element
            };
            createController = function() {
                $injector.get('$controller')("Cr_objet_craftDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'craftApp:cr_objet_craftUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
