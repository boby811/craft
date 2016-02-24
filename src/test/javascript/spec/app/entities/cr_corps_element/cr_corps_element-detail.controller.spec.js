'use strict';

describe('Controller Tests', function() {

    describe('Cr_corps_element Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockCr_corps_element, MockCr_corps, MockCr_element;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockCr_corps_element = jasmine.createSpy('MockCr_corps_element');
            MockCr_corps = jasmine.createSpy('MockCr_corps');
            MockCr_element = jasmine.createSpy('MockCr_element');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Cr_corps_element': MockCr_corps_element,
                'Cr_corps': MockCr_corps,
                'Cr_element': MockCr_element
            };
            createController = function() {
                $injector.get('$controller')("Cr_corps_elementDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'craftApp:cr_corps_elementUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
