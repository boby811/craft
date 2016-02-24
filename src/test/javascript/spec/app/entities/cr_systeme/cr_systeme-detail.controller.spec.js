'use strict';

describe('Controller Tests', function() {

    describe('Cr_systeme Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockCr_systeme, MockCr_systeme_element, MockCr_corps;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockCr_systeme = jasmine.createSpy('MockCr_systeme');
            MockCr_systeme_element = jasmine.createSpy('MockCr_systeme_element');
            MockCr_corps = jasmine.createSpy('MockCr_corps');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Cr_systeme': MockCr_systeme,
                'Cr_systeme_element': MockCr_systeme_element,
                'Cr_corps': MockCr_corps
            };
            createController = function() {
                $injector.get('$controller')("Cr_systemeDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'craftApp:cr_systemeUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
