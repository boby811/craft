'use strict';

angular.module('craftApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('cr_objet_craft', {
                parent: 'entity',
                url: '/cr_objet_crafts',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'craftApp.cr_objet_craft.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/cr_objet_craft/cr_objet_crafts.html',
                        controller: 'Cr_objet_craftController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('cr_objet_craft');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('cr_objet_craft.detail', {
                parent: 'entity',
                url: '/cr_objet_craft/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'craftApp.cr_objet_craft.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/cr_objet_craft/cr_objet_craft-detail.html',
                        controller: 'Cr_objet_craftDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('cr_objet_craft');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Cr_objet_craft', function($stateParams, Cr_objet_craft) {
                        return Cr_objet_craft.get({id : $stateParams.id});
                    }]
                }
            })
            .state('cr_objet_craft.new', {
                parent: 'cr_objet_craft',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/cr_objet_craft/cr_objet_craft-dialog.html',
                        controller: 'Cr_objet_craftDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    oc_quantite_element: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('cr_objet_craft', null, { reload: true });
                    }, function() {
                        $state.go('cr_objet_craft');
                    })
                }]
            })
            .state('cr_objet_craft.edit', {
                parent: 'cr_objet_craft',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/cr_objet_craft/cr_objet_craft-dialog.html',
                        controller: 'Cr_objet_craftDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Cr_objet_craft', function(Cr_objet_craft) {
                                return Cr_objet_craft.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('cr_objet_craft', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('cr_objet_craft.delete', {
                parent: 'cr_objet_craft',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/cr_objet_craft/cr_objet_craft-delete-dialog.html',
                        controller: 'Cr_objet_craftDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Cr_objet_craft', function(Cr_objet_craft) {
                                return Cr_objet_craft.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('cr_objet_craft', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
