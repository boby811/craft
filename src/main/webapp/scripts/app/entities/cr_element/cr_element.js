'use strict';

angular.module('craftApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('cr_element', {
                parent: 'entity',
                url: '/cr_elements',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'craftApp.cr_element.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/cr_element/cr_elements.html',
                        controller: 'Cr_elementController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('cr_element');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('cr_element.detail', {
                parent: 'entity',
                url: '/cr_element/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'craftApp.cr_element.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/cr_element/cr_element-detail.html',
                        controller: 'Cr_elementDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('cr_element');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Cr_element', function($stateParams, Cr_element) {
                        return Cr_element.get({id : $stateParams.id});
                    }]
                }
            })
            .state('cr_element.new', {
                parent: 'cr_element',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/cr_element/cr_element-dialog.html',
                        controller: 'Cr_elementDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    el_nom_court_fr_fr: null,
                                    el_nom_long_fr_fr: null,
                                    el_description_fr_fr: null,
                                    el_num_atomique: null,
                                    el_point_fusion: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('cr_element', null, { reload: true });
                    }, function() {
                        $state.go('cr_element');
                    })
                }]
            })
            .state('cr_element.edit', {
                parent: 'cr_element',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/cr_element/cr_element-dialog.html',
                        controller: 'Cr_elementDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Cr_element', function(Cr_element) {
                                return Cr_element.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('cr_element', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('cr_element.delete', {
                parent: 'cr_element',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/cr_element/cr_element-delete-dialog.html',
                        controller: 'Cr_elementDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Cr_element', function(Cr_element) {
                                return Cr_element.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('cr_element', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
