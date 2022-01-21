package com.iot.server.application.action.save_ts;

import com.iot.server.application.action.RuleNode;
import com.iot.server.application.action.RuleNodeAction;
import com.iot.server.application.message.RuleNodeMsg;
import com.iot.server.common.utils.GsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.jeasy.rules.api.Action;
import org.jeasy.rules.api.Facts;

@Slf4j
@RuleNode(
        type = "ACTION",
        name = "save timeseries",
        configClazz = SaveTsConfiguration.class
)
public class SaveTsAction implements RuleNodeAction {

    private SaveTsConfiguration config;

    @Override
    public void init(String config) {
        this.config = GsonUtils.fromJson(config, SaveTsConfiguration.class);
    }

    @Override
    public void execute(Facts facts) throws Exception {
        log.trace("{}", facts);
        RuleNodeMsg msg = facts.get("msg");
    }

}
