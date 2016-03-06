'use strict';

angular.module('craftApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('cr_corps_element', {
                parent: 'entity',
                url: '/cr_corps_elements',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'craftApp.cr_corps_element.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/cr_corps_element/cr_corps_elements.html',
                        controller: 'Cr_corps_elementController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('cr_corps_element');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('cr_corps_element.detail', {
                parent: 'entity',
                url: '/cr_corps_element/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'craftApp.cr_corps_element.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/cr_corps_element/cr_corps_element-detail.html',
                        controller: 'Cr_corps_elementDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('cr_corps_element');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Cr_corps_element', function($stateParams, Cr_corps_element) {
                        return Cr_corps_element.get({id : $stateParams.id});
                    }]
                }
            })
            .state('cr_corps_element.new', {
                parent: 'cr_corps_element',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/cr_corps_element/cr_corps_element-detail.html',
                        controller: 'Cr_corps_elementDetailController'
                    }
                },      
                resolve: {
                    entity: function () {
                        return {
                            ce_quantite: null,
                            id: null
                        };
                    }
                }
            })
            .state('cr_corps_element.edit', {
                parent: 'cr_corps_element',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/cr_corps_element/cr_corps_element-dialog.html',
                        controller: 'Cr_corps_elementDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Cr_corps_element', function(Cr_corps_element) {
                                return Cr_corps_element.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('cr_corps_element', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('cr_corps_element.delete', {
                parent: 'cr_corps_element',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/cr_corps_element/cr_corps_element-delete-dialog.html',
                        controller: 'Cr_corps_elementDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Cr_corps_element', function(Cr_corps_element) {
                                return Cr_corps_element.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('cr_corps_element', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
