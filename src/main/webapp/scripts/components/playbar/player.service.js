'use strict';

angular.module('tsmusicbotApp')
    .factory('Player', function ($rootScope, $cookies, $http, $q) {
        var stompClient = null;
        var subscriber = null;
        var listener = $q.defer();
        var connected = $q.defer();
        var alreadyConnectedOnce = false;
        function sendSongCommand(command) {
            if (stompClient != null && stompClient.connected) {
                stompClient
                    .send('/topic/player_commands',
                    {},
                    JSON.stringify(command));
            }
        }
        function subscribe() {
            subscriber = stompClient.subscribe("/topic/player_events", function(data) {
                listener.notify(JSON.parse(data.body));
            });
        }
        function unsubscribe() {
            if (subscriber != null) {
                subscriber.unsubscribe();
            }
        }
        return {
            connect: function () {
                //building absolute path so that websocket doesnt fail when deploying with a context path
                var loc = window.location;
                var url = '//' + loc.host + loc.pathname + 'websocket/tracker';
                var socket = new SockJS(url);
                stompClient = Stomp.over(socket);
                var headers = {};
                headers['X-CSRF-TOKEN'] = $cookies[$http.defaults.xsrfCookieName];
                stompClient.connect(headers, function(frame) {
                    subscribe();
                    connected.resolve("success");
                    if (!alreadyConnectedOnce) {
                        alreadyConnectedOnce = true;
                    }
                });
            },
            receive: function() {
                return listener.promise;
            },
            disconnect: function() {
                if (stompClient != null) {
                    unsubscribe();
                    stompClient.disconnect();
                    stompClient = null;
                }
            }
        };
    });
