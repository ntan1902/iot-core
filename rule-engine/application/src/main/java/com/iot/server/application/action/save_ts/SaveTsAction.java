package com.iot.server.application.action.save_ts;

import com.iot.server.application.action.RuleNode;
import com.iot.server.application.action.RuleNodeAction;
import com.iot.server.application.action.ctx.RuleNodeCtx;
import com.iot.server.application.message.RuleNodeMsg;
import com.iot.server.common.enums.MsgType;
import com.iot.server.common.model.TelemetryMsg;
import com.iot.server.common.queue.QueueMsg;
import com.iot.server.common.utils.GsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.jeasy.rules.api.Facts;
import org.springframework.amqp.AmqpException;

import java.util.UUID;

@Slf4j
@RuleNode(
        type = "ACTION",
        name = "save timeseries",
        configClazz = SaveTsConfiguration.class
)
public class SaveTsAction implements RuleNodeAction {

    private SaveTsConfiguration config;
    private RuleNodeCtx ctx;

    @Override
    public void init(RuleNodeCtx ctx, String config) {
        this.ctx = ctx;
        this.config = GsonUtils.fromJson(config, SaveTsConfiguration.class);
    }

    @Override
    public void execute(Facts facts) throws Exception {
        log.trace("{}", facts);
        RuleNodeMsg msg = getMsg(facts);

        TelemetryMsg telemetryMsg = GsonUtils.fromJson(msg.getData(), TelemetryMsg.class);

        String type = "";
        if (config.isSkipLatestPersistence()) {
            type = MsgType.SAVE_TELEMETRY.name();
        } else {
            type = MsgType.SAVE_LATEST_TELEMETRY.name();
        }

        try {
            ctx.getTelemetryTemplate().convertAndSend(
                    GsonUtils.toJson(new QueueMsg<>(UUID.randomUUID(), telemetryMsg, type, msg.getUserIds()))
            );

            setSuccess(facts);
        } catch (AmqpException ex) {
            setFailure(facts);
        }
    }

}
