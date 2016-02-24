'use strict';

describe('Controller Tests', function() {

    describe('Cr_categorie_craft Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockCr_categorie_craft, MockCr_image, MockCr_objet_craft;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockCr_categorie_craft = jasmine.createSpy('MockCr_categorie_craft');
            MockCr_image = jasmine.createSpy('MockCr_image');
            MockCr_objet_craft = jasmine.createSpy('MockCr_objet_craft');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Cr_categorie_craft': MockCr_categorie_craft,
                'Cr_image': MockCr_image,
                'Cr_objet_craft': MockCr_objet_craft
            };
            createController = function() {
                $injector.get('$controller')("Cr_categorie_craftDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'craftApp:cr_categorie_craftUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
