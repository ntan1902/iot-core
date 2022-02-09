package com.iot.server.domain;

import com.iot.server.common.model.TelemetryMsg;
import com.iot.server.common.utils.GsonUtils;
import com.iot.server.domain.model.TelemetrySocketMsg;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class WebSocketServiceImpl implements WebSocketService {
    private final SimpMessageSendingOperations messagingTemplate;

    @Override
    public void sendTelemetry(Set<UUID> userIds, TelemetryMsg telemetryMsg) {
        TelemetrySocketMsg telemetrySocketMsg = new TelemetrySocketMsg();

        telemetrySocketMsg.setEntityId(telemetryMsg.getEntityId());
        telemetrySocketMsg.setKvs(telemetryMsg.getKvs());

        String msg = GsonUtils.toJson(telemetrySocketMsg);
        for (UUID userId : userIds) {
            try {
                messagingTemplate.convertAndSend("/topic/telemetry-" + userId, msg);
            } catch (MessagingException ex) {
                log.error("Failed to publish message {}", msg, ex);
            }
        }
    }

    @Override
    public void sendDebugMsg(Set<UUID> userIds, String msg) {
        for (UUID userId : userIds) {
            try {
                messagingTemplate.convertAndSend("/topic/debug-" + userId, msg);
            } catch (MessagingException ex) {
                log.error("Failed to publish message {}", msg, ex);
            }
        }
    }
}
