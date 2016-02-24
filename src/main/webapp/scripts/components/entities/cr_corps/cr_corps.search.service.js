'use strict';

angular.module('craftApp')
    .factory('Cr_corpsSearch', function ($resource) {
        return $resource('api/_search/cr_corpss/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
