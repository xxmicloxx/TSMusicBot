package com.tsmusicbot.config;

import com.tsmusicbot.security.AuthoritiesConstants;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;

@Configuration
public class WebsocketSecurityConfiguration extends AbstractSecurityWebSocketMessageBrokerConfigurer {

    @Override
    protected void configureInbound(MessageSecurityMetadataSourceRegistry messages) {
        messages
            // message types other than MESSAGE and SUBSCRIBE
            .nullDestMatcher().permitAll()
            // matches any destination that starts with /rooms/
            .simpMessageDestMatchers("/topic/player_commands").hasAuthority(AuthoritiesConstants.ADMIN)
            .simpSubscribeDestMatchers("/topic/player_events").permitAll()
            .simpMessageDestMatchers("/topic/vote_commands").authenticated()
            .simpSubscribeDestMatchers("/topic/vote_events").permitAll()
            // (i.e. cannot send messages directly to /topic/, /queue/)
            // (i.e. cannot subscribe to /topic/messages/* to get messages sent to
            // /topic/messages-user<id>)
            .simpTypeMatchers(SimpMessageType.MESSAGE, SimpMessageType.SUBSCRIBE).denyAll()
            // catch all
            .anyMessage().denyAll();
    }

    /**
     * Disables CSRF for Websockets.
     */
    @Override
    protected boolean sameOriginDisabled() {
        return true;
    }
}
