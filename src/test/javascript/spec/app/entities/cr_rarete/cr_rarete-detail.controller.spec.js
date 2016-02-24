'use strict';

describe('Controller Tests', function() {

    describe('Cr_rarete Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockCr_rarete, MockCr_element;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockCr_rarete = jasmine.createSpy('MockCr_rarete');
            MockCr_element = jasmine.createSpy('MockCr_element');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Cr_rarete': MockCr_rarete,
                'Cr_element': MockCr_element
            };
            createController = function() {
                $injector.get('$controller')("Cr_rareteDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'craftApp:cr_rareteUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
