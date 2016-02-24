'use strict';

describe('Controller Tests', function() {

    describe('Cr_element Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockCr_element, MockCr_rarete, MockCr_image, MockCr_corps_element, MockCr_objet_craft, MockCr_systeme_element;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockCr_element = jasmine.createSpy('MockCr_element');
            MockCr_rarete = jasmine.createSpy('MockCr_rarete');
            MockCr_image = jasmine.createSpy('MockCr_image');
            MockCr_corps_element = jasmine.createSpy('MockCr_corps_element');
            MockCr_objet_craft = jasmine.createSpy('MockCr_objet_craft');
            MockCr_systeme_element = jasmine.createSpy('MockCr_systeme_element');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Cr_element': MockCr_element,
                'Cr_rarete': MockCr_rarete,
                'Cr_image': MockCr_image,
                'Cr_corps_element': MockCr_corps_element,
                'Cr_objet_craft': MockCr_objet_craft,
                'Cr_systeme_element': MockCr_systeme_element
            };
            createController = function() {
                $injector.get('$controller')("Cr_elementDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'craftApp:cr_elementUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
