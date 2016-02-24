'use strict';

angular.module('craftApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('cr_type_corps', {
                parent: 'entity',
                url: '/cr_type_corpss',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'craftApp.cr_type_corps.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/cr_type_corps/cr_type_corpss.html',
                        controller: 'Cr_type_corpsController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('cr_type_corps');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('cr_type_corps.detail', {
                parent: 'entity',
                url: '/cr_type_corps/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'craftApp.cr_type_corps.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/cr_type_corps/cr_type_corps-detail.html',
                        controller: 'Cr_type_corpsDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('cr_type_corps');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Cr_type_corps', function($stateParams, Cr_type_corps) {
                        return Cr_type_corps.get({id : $stateParams.id});
                    }]
                }
            })
            .state('cr_type_corps.new', {
                parent: 'cr_type_corps',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/cr_type_corps/cr_type_corps-dialog.html',
                        controller: 'Cr_type_corpsDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    tc_libelle_fr_fr: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('cr_type_corps', null, { reload: true });
                    }, function() {
                        $state.go('cr_type_corps');
                    })
                }]
            })
            .state('cr_type_corps.edit', {
                parent: 'cr_type_corps',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/cr_type_corps/cr_type_corps-dialog.html',
                        controller: 'Cr_type_corpsDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Cr_type_corps', function(Cr_type_corps) {
                                return Cr_type_corps.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('cr_type_corps', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('cr_type_corps.delete', {
                parent: 'cr_type_corps',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/cr_type_corps/cr_type_corps-delete-dialog.html',
                        controller: 'Cr_type_corpsDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Cr_type_corps', function(Cr_type_corps) {
                                return Cr_type_corps.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('cr_type_corps', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
