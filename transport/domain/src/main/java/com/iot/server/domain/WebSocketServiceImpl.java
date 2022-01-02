package com.iot.server.domain;

import com.iot.server.common.model.Kv;
import com.iot.server.common.model.PostTelemetryMsg;
import com.iot.server.common.utils.GsonUtils;
import com.iot.server.domain.model.KvSocketMsg;
import com.iot.server.domain.model.PostTelemetrySocketMsg;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class WebSocketServiceImpl implements WebSocketService {
    private final SimpMessageSendingOperations messagingTemplate;

    @Override
    public void send(PostTelemetryMsg postTelemetryMsg) {
        PostTelemetrySocketMsg telemetrySocketMsg = new PostTelemetrySocketMsg();
        telemetrySocketMsg.setDeviceId(postTelemetryMsg.getEntityId());

        List<KvSocketMsg> kvs = new ArrayList<>();
        for (Kv kv : postTelemetryMsg.getKvs()) {
            KvSocketMsg kvSocketMsg = new KvSocketMsg();

            kvSocketMsg.setKey(kv.getKey());
            kvSocketMsg.setTs(postTelemetryMsg.getTs());
            kvSocketMsg.setType(kv.getType());

            if (kv.getDoubleV() != null) {
                kvSocketMsg.setValue(kv.getDoubleV());
            }
            if (kv.getLongV() != null) {
                kvSocketMsg.setValue(kv.getLongV());
            }
            if (kv.getBoolV() != null) {
                kvSocketMsg.setValue(kv.getBoolV());
            }
            if (kv.getStringV() != null) {
                kvSocketMsg.setValue(kv.getStringV());
            }
            if (kv.getJsonV() != null) {
                kvSocketMsg.setValue(kv.getJsonV());
            }
            kvs.add(kvSocketMsg);
        }
        telemetrySocketMsg.setKvs(kvs);

        String userId = postTelemetryMsg.getUserId().toString();
        String msg = GsonUtils.toJson(telemetrySocketMsg);
        try {
            messagingTemplate.convertAndSend("/topic/telemetry-" + userId, msg);
        } catch (MessagingException ex) {
            log.error("Failed to publish message [{}]", msg, ex);
        }
    }
}
