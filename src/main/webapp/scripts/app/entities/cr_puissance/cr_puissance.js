'use strict';

angular.module('craftApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('cr_puissance', {
                parent: 'entity',
                url: '/cr_puissances',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'craftApp.cr_puissance.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/cr_puissance/cr_puissances.html',
                        controller: 'Cr_puissanceController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('cr_puissance');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('cr_puissance.detail', {
                parent: 'entity',
                url: '/cr_puissance/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'craftApp.cr_puissance.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/cr_puissance/cr_puissance-detail.html',
                        controller: 'Cr_puissanceDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('cr_puissance');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Cr_puissance', function($stateParams, Cr_puissance) {
                        return Cr_puissance.get({id : $stateParams.id});
                    }]
                }
            })
            .state('cr_puissance.new', {
                parent: 'cr_puissance',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/cr_puissance/cr_puissance-dialog.html',
                        controller: 'Cr_puissanceDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    pu_libelle: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('cr_puissance', null, { reload: true });
                    }, function() {
                        $state.go('cr_puissance');
                    })
                }]
            })
            .state('cr_puissance.edit', {
                parent: 'cr_puissance',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/cr_puissance/cr_puissance-dialog.html',
                        controller: 'Cr_puissanceDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Cr_puissance', function(Cr_puissance) {
                                return Cr_puissance.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('cr_puissance', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('cr_puissance.delete', {
                parent: 'cr_puissance',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/cr_puissance/cr_puissance-delete-dialog.html',
                        controller: 'Cr_puissanceDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Cr_puissance', function(Cr_puissance) {
                                return Cr_puissance.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('cr_puissance', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
