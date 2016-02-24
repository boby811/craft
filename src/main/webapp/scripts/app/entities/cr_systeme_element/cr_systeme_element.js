'use strict';

angular.module('craftApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('cr_systeme_element', {
                parent: 'entity',
                url: '/cr_systeme_elements',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'craftApp.cr_systeme_element.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/cr_systeme_element/cr_systeme_elements.html',
                        controller: 'Cr_systeme_elementController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('cr_systeme_element');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('cr_systeme_element.detail', {
                parent: 'entity',
                url: '/cr_systeme_element/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'craftApp.cr_systeme_element.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/cr_systeme_element/cr_systeme_element-detail.html',
                        controller: 'Cr_systeme_elementDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('cr_systeme_element');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Cr_systeme_element', function($stateParams, Cr_systeme_element) {
                        return Cr_systeme_element.get({id : $stateParams.id});
                    }]
                }
            })
            .state('cr_systeme_element.new', {
                parent: 'cr_systeme_element',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/cr_systeme_element/cr_systeme_element-dialog.html',
                        controller: 'Cr_systeme_elementDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    se_quantite: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('cr_systeme_element', null, { reload: true });
                    }, function() {
                        $state.go('cr_systeme_element');
                    })
                }]
            })
            .state('cr_systeme_element.edit', {
                parent: 'cr_systeme_element',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/cr_systeme_element/cr_systeme_element-dialog.html',
                        controller: 'Cr_systeme_elementDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Cr_systeme_element', function(Cr_systeme_element) {
                                return Cr_systeme_element.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('cr_systeme_element', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('cr_systeme_element.delete', {
                parent: 'cr_systeme_element',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/cr_systeme_element/cr_systeme_element-delete-dialog.html',
                        controller: 'Cr_systeme_elementDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Cr_systeme_element', function(Cr_systeme_element) {
                                return Cr_systeme_element.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('cr_systeme_element', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
