 'use strict';

angular.module('tsmusicbotApp')
    .factory('notificationInterceptor', function ($q, AlertService) {
        return {
            response: function(response) {
                var alertKey = response.headers('X-tsmusicbotApp-alert');
                if (angular.isString(alertKey)) {
                    AlertService.success(alertKey, { param : response.headers('X-tsmusicbotApp-params')});
                }
                return response;
            },
        };
    });