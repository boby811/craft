'use strict';

angular.module('craftApp')
    .factory('Cr_systemeSearch', function ($resource) {
        return $resource('api/_search/cr_systemes/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
