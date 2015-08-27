package com.tsmusicbot.web.websocket;

import com.tsmusicbot.web.websocket.dto.PlayerSnapshotDTO;
import com.tsmusicbot.web.websocket.dto.PlayerStateDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;

import javax.inject.Inject;
import java.security.Principal;

@Controller
public class PlayerService {

    private static final Logger log = LoggerFactory.getLogger(PlayerService.class);

    @Inject
    SimpMessageSendingOperations messagingTemplate;

    @SubscribeMapping("/topic/player_commands")
    @SendTo("/topic/player_events")
    public PlayerSnapshotDTO changePlayerState(@Payload PlayerStateDTO stateDTO,
                                               StompHeaderAccessor stompHeaderAccessor, Principal principal) {

        return null;
    }
}
