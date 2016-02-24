'use strict';

angular.module('craftApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('cr_image', {
                parent: 'entity',
                url: '/cr_images',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'craftApp.cr_image.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/cr_image/cr_images.html',
                        controller: 'Cr_imageController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('cr_image');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('cr_image.detail', {
                parent: 'entity',
                url: '/cr_image/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'craftApp.cr_image.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/cr_image/cr_image-detail.html',
                        controller: 'Cr_imageDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('cr_image');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Cr_image', function($stateParams, Cr_image) {
                        return Cr_image.get({id : $stateParams.id});
                    }]
                }
            })
            .state('cr_image.new', {
                parent: 'cr_image',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/cr_image/cr_image-dialog.html',
                        controller: 'Cr_imageDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    im_url: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('cr_image', null, { reload: true });
                    }, function() {
                        $state.go('cr_image');
                    })
                }]
            })
            .state('cr_image.edit', {
                parent: 'cr_image',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/cr_image/cr_image-dialog.html',
                        controller: 'Cr_imageDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Cr_image', function(Cr_image) {
                                return Cr_image.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('cr_image', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('cr_image.delete', {
                parent: 'cr_image',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/cr_image/cr_image-delete-dialog.html',
                        controller: 'Cr_imageDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Cr_image', function(Cr_image) {
                                return Cr_image.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('cr_image', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
