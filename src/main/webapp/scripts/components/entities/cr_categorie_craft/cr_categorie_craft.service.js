'use strict';

angular.module('craftApp')
    .factory('Cr_categorie_craft', function ($resource, DateUtils) {
        return $resource('api/cr_categorie_crafts/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
