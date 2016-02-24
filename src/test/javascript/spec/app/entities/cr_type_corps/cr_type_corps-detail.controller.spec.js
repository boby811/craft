'use strict';

describe('Controller Tests', function() {

    describe('Cr_type_corps Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockCr_type_corps, MockCr_corps;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockCr_type_corps = jasmine.createSpy('MockCr_type_corps');
            MockCr_corps = jasmine.createSpy('MockCr_corps');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Cr_type_corps': MockCr_type_corps,
                'Cr_corps': MockCr_corps
            };
            createController = function() {
                $injector.get('$controller')("Cr_type_corpsDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'craftApp:cr_type_corpsUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
