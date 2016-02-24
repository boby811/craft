'use strict';

angular.module('craftApp')
    .factory('Cr_type_corpsSearch', function ($resource) {
        return $resource('api/_search/cr_type_corpss/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
