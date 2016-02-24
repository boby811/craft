'use strict';

describe('Controller Tests', function() {

    describe('Cr_systeme_element Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockCr_systeme_element, MockCr_element, MockCr_systeme;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockCr_systeme_element = jasmine.createSpy('MockCr_systeme_element');
            MockCr_element = jasmine.createSpy('MockCr_element');
            MockCr_systeme = jasmine.createSpy('MockCr_systeme');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Cr_systeme_element': MockCr_systeme_element,
                'Cr_element': MockCr_element,
                'Cr_systeme': MockCr_systeme
            };
            createController = function() {
                $injector.get('$controller')("Cr_systeme_elementDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'craftApp:cr_systeme_elementUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
