'use strict';

angular.module('craftApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('cr_systeme', {
                parent: 'entity',
                url: '/cr_systemes',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'craftApp.cr_systeme.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/cr_systeme/cr_systemes.html',
                        controller: 'Cr_systemeController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('cr_systeme');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('cr_systeme.detail', {
                parent: 'entity',
                url: '/cr_systeme/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'craftApp.cr_systeme.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/cr_systeme/cr_systeme-detail.html',
                        controller: 'Cr_systemeDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('cr_systeme');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Cr_systeme', function($stateParams, Cr_systeme) {
                        return Cr_systeme.get({id : $stateParams.id});
                    }]
                }
            })
            .state('cr_systeme.new', {
                parent: 'cr_systeme',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/cr_systeme/cr_systeme-dialog.html',
                        controller: 'Cr_systemeDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    sy_nom_fr_fr: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('cr_systeme', null, { reload: true });
                    }, function() {
                        $state.go('cr_systeme');
                    })
                }]
            })
            .state('cr_systeme.edit', {
                parent: 'cr_systeme',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/cr_systeme/cr_systeme-dialog.html',
                        controller: 'Cr_systemeDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Cr_systeme', function(Cr_systeme) {
                                return Cr_systeme.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('cr_systeme', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('cr_systeme.delete', {
                parent: 'cr_systeme',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/cr_systeme/cr_systeme-delete-dialog.html',
                        controller: 'Cr_systemeDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Cr_systeme', function(Cr_systeme) {
                                return Cr_systeme.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('cr_systeme', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
