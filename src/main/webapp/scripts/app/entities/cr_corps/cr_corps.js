'use strict';

angular.module('craftApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('cr_corps', {
                parent: 'entity',
                url: '/cr_corpss',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'craftApp.cr_corps.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/cr_corps/cr_corpss.html',
                        controller: 'Cr_corpsController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('cr_corps');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('cr_corps.detail', {
                parent: 'entity',
                url: '/cr_corps/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'craftApp.cr_corps.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/cr_corps/cr_corps-detail.html',
                        controller: 'Cr_corpsDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('cr_corps');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Cr_corps', function($stateParams, Cr_corps) {
                        return Cr_corps.get({id : $stateParams.id});
                    }]
                }
            })
            .state('cr_corps.new', {
                parent: 'cr_corps',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/cr_corps/cr_corps-dialog.html',
                        controller: 'Cr_corpsDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    co_nom_fr_fr: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('cr_corps', null, { reload: true });
                    }, function() {
                        $state.go('cr_corps');
                    })
                }]
            })
            .state('cr_corps.edit', {
                parent: 'cr_corps',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/cr_corps/cr_corps-dialog.html',
                        controller: 'Cr_corpsDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Cr_corps', function(Cr_corps) {
                                return Cr_corps.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('cr_corps', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('cr_corps.delete', {
                parent: 'cr_corps',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/cr_corps/cr_corps-delete-dialog.html',
                        controller: 'Cr_corpsDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Cr_corps', function(Cr_corps) {
                                return Cr_corps.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('cr_corps', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
