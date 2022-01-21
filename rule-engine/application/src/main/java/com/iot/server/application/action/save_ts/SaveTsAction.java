package com.iot.server.application.action.save_ts;

import com.iot.server.application.action.RuleNode;
import com.iot.server.application.action.RuleNodeAction;
import com.iot.server.application.message.RuleNodeMsg;
import lombok.extern.slf4j.Slf4j;
import org.jeasy.rules.api.Action;
import org.jeasy.rules.api.Facts;

@Slf4j
@RuleNode(
        type = "ACTION",
        name = "save timeseries",
        configClazz = SaveTsConfiguration.class
)
public class SaveTsAction implements Action, RuleNodeAction {

    @Override
    public void execute(Facts facts) throws Exception {
        log.trace("{}", facts);
        onReceived(facts.get("msg"));
    }

    @Override
    public void init(String configuration) {
    }

    @Override
    public void onReceived(RuleNodeMsg msg) {

    }


}
