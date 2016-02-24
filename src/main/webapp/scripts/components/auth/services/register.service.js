'use strict';

angular.module('craftApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


