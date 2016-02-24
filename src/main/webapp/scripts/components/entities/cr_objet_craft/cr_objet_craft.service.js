'use strict';

angular.module('craftApp')
    .factory('Cr_objet_craft', function ($resource, DateUtils) {
        return $resource('api/cr_objet_crafts/:id', {}, {
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
