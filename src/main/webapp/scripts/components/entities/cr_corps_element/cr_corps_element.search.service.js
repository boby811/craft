'use strict';

angular.module('craftApp')
    .factory('Cr_corps_elementSearch', function ($resource) {
        return $resource('api/_search/cr_corps_elements/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
