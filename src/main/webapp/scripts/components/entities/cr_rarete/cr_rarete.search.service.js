'use strict';

angular.module('craftApp')
    .factory('Cr_rareteSearch', function ($resource) {
        return $resource('api/_search/cr_raretes/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
