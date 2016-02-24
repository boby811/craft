'use strict';

angular.module('craftApp')
    .factory('Cr_categorie_craftSearch', function ($resource) {
        return $resource('api/_search/cr_categorie_crafts/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
