'use strict';

describe('Controller Tests', function() {

    describe('Cr_corps Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockCr_corps, MockCr_corps_element, MockCr_type_corps, MockCr_systeme;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockCr_corps = jasmine.createSpy('MockCr_corps');
            MockCr_corps_element = jasmine.createSpy('MockCr_corps_element');
            MockCr_type_corps = jasmine.createSpy('MockCr_type_corps');
            MockCr_systeme = jasmine.createSpy('MockCr_systeme');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Cr_corps': MockCr_corps,
                'Cr_corps_element': MockCr_corps_element,
                'Cr_type_corps': MockCr_type_corps,
                'Cr_systeme': MockCr_systeme
            };
            createController = function() {
                $injector.get('$controller')("Cr_corpsDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'craftApp:cr_corpsUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
