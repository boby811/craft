'use strict';

describe('Controller Tests', function() {

    describe('Cr_image Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockCr_image, MockCr_element, MockCr_categorie_craft;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockCr_image = jasmine.createSpy('MockCr_image');
            MockCr_element = jasmine.createSpy('MockCr_element');
            MockCr_categorie_craft = jasmine.createSpy('MockCr_categorie_craft');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Cr_image': MockCr_image,
                'Cr_element': MockCr_element,
                'Cr_categorie_craft': MockCr_categorie_craft
            };
            createController = function() {
                $injector.get('$controller')("Cr_imageDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'craftApp:cr_imageUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
