'use strict';

angular.module('tsmusicbotApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


