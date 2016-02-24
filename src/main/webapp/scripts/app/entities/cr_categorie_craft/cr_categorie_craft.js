'use strict';

angular.module('craftApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('cr_categorie_craft', {
                parent: 'entity',
                url: '/cr_categorie_crafts',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'craftApp.cr_categorie_craft.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/cr_categorie_craft/cr_categorie_crafts.html',
                        controller: 'Cr_categorie_craftController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('cr_categorie_craft');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('cr_categorie_craft.detail', {
                parent: 'entity',
                url: '/cr_categorie_craft/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'craftApp.cr_categorie_craft.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/cr_categorie_craft/cr_categorie_craft-detail.html',
                        controller: 'Cr_categorie_craftDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('cr_categorie_craft');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Cr_categorie_craft', function($stateParams, Cr_categorie_craft) {
                        return Cr_categorie_craft.get({id : $stateParams.id});
                    }]
                }
            })
            .state('cr_categorie_craft.new', {
                parent: 'cr_categorie_craft',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/cr_categorie_craft/cr_categorie_craft-dialog.html',
                        controller: 'Cr_categorie_craftDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    cc_nom_court_fr_fr: null,
                                    cc_nom_long_fr_fr: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('cr_categorie_craft', null, { reload: true });
                    }, function() {
                        $state.go('cr_categorie_craft');
                    })
                }]
            })
            .state('cr_categorie_craft.edit', {
                parent: 'cr_categorie_craft',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/cr_categorie_craft/cr_categorie_craft-dialog.html',
                        controller: 'Cr_categorie_craftDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Cr_categorie_craft', function(Cr_categorie_craft) {
                                return Cr_categorie_craft.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('cr_categorie_craft', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('cr_categorie_craft.delete', {
                parent: 'cr_categorie_craft',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/cr_categorie_craft/cr_categorie_craft-delete-dialog.html',
                        controller: 'Cr_categorie_craftDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Cr_categorie_craft', function(Cr_categorie_craft) {
                                return Cr_categorie_craft.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('cr_categorie_craft', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
