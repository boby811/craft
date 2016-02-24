'use strict';

angular.module('craftApp')
    .factory('Cr_imageSearch', function ($resource) {
        return $resource('api/_search/cr_images/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
