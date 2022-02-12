package com.iot.server.application.action.msg_type_switch;

import com.iot.server.application.action.AbstractRuleNodeAction;
import com.iot.server.application.action.EmptyConfiguration;
import com.iot.server.application.action.RuleNode;
import com.iot.server.application.action.RuleNodeAction;
import com.iot.server.application.action.ctx.RuleNodeCtx;
import com.iot.server.application.message.RuleNodeMsg;
import com.iot.server.common.enums.MsgType;
import com.iot.server.common.utils.GsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.jeasy.rules.api.Facts;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Slf4j
@RuleNode(
        type = "ACTION",
        name = "message type switch",
        relationNames = {"Post attributes", "Post telemetry", "RPC Request from Device", "RPC Request to Device", "RPC Queued", "RPC Sent", "RPC Delivered", "RPC Successful", "RPC Timeout", "RPC Expired", "RPC Failed", "RPC Deleted",
                "Activity Event", "Inactivity Event", "Connect Event", "Disconnect Event", "Entity Created", "Entity Updated", "Entity Deleted", "Entity Assigned",
                "Entity Unassigned", "Attributes Updated", "Attributes Deleted", "Alarm Acknowledged", "Alarm Cleared", "Other", "Entity Assigned From Tenant", "Entity Assigned To Tenant",
                "Timeseries Updated", "Timeseries Deleted", "Success", "Failure"},
        configClazz = EmptyConfiguration.class
)
public class MsgTypeSwitchAction extends AbstractRuleNodeAction {

    private EmptyConfiguration config;

    @Override
    public void init(RuleNodeCtx ctx, UUID ruleNodeId, String config) {
        this.ctx = ctx;
        this.ruleNodeId = ruleNodeId;
    }

    @Override
    protected void initConfig(String config) {
        this.config = GsonUtils.fromJson(config, EmptyConfiguration.class);
    }

    @Override
    protected void executeMsg(RuleNodeMsg msg, Set<String> relationNames) {
        if (msg.getType().equals(MsgType.POST_TELEMETRY_REQUEST.name())) {
            relationNames.add("Post telemetry");
            setSuccess(relationNames);
        } else {
            setFailure(relationNames);
        }

    }
}
