'use strict';

angular.module('craftApp')
    .factory('Cr_systeme_elementSearch', function ($resource) {
        return $resource('api/_search/cr_systeme_elements/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
