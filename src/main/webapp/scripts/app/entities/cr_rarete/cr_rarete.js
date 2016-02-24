'use strict';

angular.module('craftApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('cr_rarete', {
                parent: 'entity',
                url: '/cr_raretes',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'craftApp.cr_rarete.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/cr_rarete/cr_raretes.html',
                        controller: 'Cr_rareteController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('cr_rarete');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('cr_rarete.detail', {
                parent: 'entity',
                url: '/cr_rarete/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'craftApp.cr_rarete.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/cr_rarete/cr_rarete-detail.html',
                        controller: 'Cr_rareteDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('cr_rarete');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Cr_rarete', function($stateParams, Cr_rarete) {
                        return Cr_rarete.get({id : $stateParams.id});
                    }]
                }
            })
            .state('cr_rarete.new', {
                parent: 'cr_rarete',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/cr_rarete/cr_rarete-dialog.html',
                        controller: 'Cr_rareteDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    ra_libelle_fr_fr: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('cr_rarete', null, { reload: true });
                    }, function() {
                        $state.go('cr_rarete');
                    })
                }]
            })
            .state('cr_rarete.edit', {
                parent: 'cr_rarete',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/cr_rarete/cr_rarete-dialog.html',
                        controller: 'Cr_rareteDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Cr_rarete', function(Cr_rarete) {
                                return Cr_rarete.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('cr_rarete', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('cr_rarete.delete', {
                parent: 'cr_rarete',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/cr_rarete/cr_rarete-delete-dialog.html',
                        controller: 'Cr_rareteDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Cr_rarete', function(Cr_rarete) {
                                return Cr_rarete.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('cr_rarete', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
