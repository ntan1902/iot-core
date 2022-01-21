package com.iot.server.application.action.msg_type_switch;

import com.iot.server.application.action.EmptyConfiguration;
import com.iot.server.application.action.RuleNode;
import com.iot.server.application.action.RuleNodeAction;
import com.iot.server.application.message.RuleNodeMsg;
import com.iot.server.common.enums.MsgType;
import com.iot.server.common.utils.GsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.jeasy.rules.api.Facts;

@Slf4j
@RuleNode(
        type = "ACTION",
        name = "message type switch",
        relationNames = {"Post attributes", "Post telemetry", "RPC Request from Device", "RPC Request to Device", "RPC Queued", "RPC Sent", "RPC Delivered", "RPC Successful", "RPC Timeout", "RPC Expired", "RPC Failed", "RPC Deleted",
                "Activity Event", "Inactivity Event", "Connect Event", "Disconnect Event", "Entity Created", "Entity Updated", "Entity Deleted", "Entity Assigned",
                "Entity Unassigned", "Attributes Updated", "Attributes Deleted", "Alarm Acknowledged", "Alarm Cleared", "Other", "Entity Assigned From Tenant", "Entity Assigned To Tenant",
                "Timeseries Updated", "Timeseries Deleted"},
        configClazz = EmptyConfiguration.class
)
public class MsgTypeSwitchAction implements RuleNodeAction {

    private EmptyConfiguration config;

    @Override
    public void init(String config) {
        this.config = GsonUtils.fromJson(config, EmptyConfiguration.class);
    }

    @Override
    public void execute(Facts facts) throws Exception {
        log.info("{}", facts);
        RuleNodeMsg msg = facts.get("msg");
        String relationName = "";

        if (msg.getType().equals(MsgType.POST_TELEMETRY_REQUEST.name())) {
            relationName = "Post telemetry";
        }

        facts.put("relationName", relationName);
    }
}
