'use strict';

angular.module('craftApp')
    .factory('Cr_puissanceSearch', function ($resource) {
        return $resource('api/_search/cr_puissances/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
