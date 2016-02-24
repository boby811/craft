'use strict';

angular.module('craftApp')
    .factory('Cr_type_corps', function ($resource, DateUtils) {
        return $resource('api/cr_type_corpss/:id', {}, {
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
