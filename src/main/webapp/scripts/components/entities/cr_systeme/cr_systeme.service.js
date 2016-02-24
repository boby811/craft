'use strict';

angular.module('craftApp')
    .factory('Cr_systeme', function ($resource, DateUtils) {
        return $resource('api/cr_systemes/:id', {}, {
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
