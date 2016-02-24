'use strict';

angular.module('craftApp')
    .factory('Cr_objet_craftSearch', function ($resource) {
        return $resource('api/_search/cr_objet_crafts/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
