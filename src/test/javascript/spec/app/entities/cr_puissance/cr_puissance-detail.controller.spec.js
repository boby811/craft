'use strict';

describe('Controller Tests', function() {

    describe('Cr_puissance Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockCr_puissance, MockCr_objet_craft;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockCr_puissance = jasmine.createSpy('MockCr_puissance');
            MockCr_objet_craft = jasmine.createSpy('MockCr_objet_craft');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Cr_puissance': MockCr_puissance,
                'Cr_objet_craft': MockCr_objet_craft
            };
            createController = function() {
                $injector.get('$controller')("Cr_puissanceDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'craftApp:cr_puissanceUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
