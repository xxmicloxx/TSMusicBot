'use strict';

angular.module('tsmusicbotApp')
    .controller('LogoutController', function (Auth) {
        Auth.logout();
    });
