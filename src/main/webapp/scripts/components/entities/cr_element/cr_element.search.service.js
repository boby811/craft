'use strict';

angular.module('craftApp')
    .factory('Cr_elementSearch', function ($resource) {
        return $resource('api/_search/cr_elements/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
