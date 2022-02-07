package com.iot.server.domain;

import com.iot.server.common.model.TelemetryMsg;
import com.iot.server.common.utils.GsonUtils;
import com.iot.server.domain.model.TelemetrySocketMsg;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class WebSocketServiceImpl implements WebSocketService {
    private final SimpMessageSendingOperations messagingTemplate;

    @Override
    public void sendTelemetry(String userId, TelemetryMsg telemetryMsg) {
        TelemetrySocketMsg telemetrySocketMsg = new TelemetrySocketMsg();

        telemetrySocketMsg.setEntityId(telemetryMsg.getEntityId());
        telemetrySocketMsg.setKvs(telemetryMsg.getKvs());

        String msg = GsonUtils.toJson(telemetrySocketMsg);
        try {
            messagingTemplate.convertAndSend("/topic/telemetry-" + userId, msg);
        } catch (MessagingException ex) {
            log.error("Failed to publish message {}", msg, ex);
        }
    }

    @Override
    public void sendDebugMsg(String userId, String msg) {
        try {
            messagingTemplate.convertAndSend("/topic/debug-" + userId, msg);
        } catch (MessagingException ex) {
            log.error("Failed to publish message {}", msg, ex);
        }
    }
}
