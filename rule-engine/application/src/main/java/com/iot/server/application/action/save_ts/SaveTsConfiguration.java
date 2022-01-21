package com.iot.server.application.action.save_ts;

import com.iot.server.application.action.ActionConfiguration;
import lombok.Data;

@Data
public class SaveTsConfiguration implements ActionConfiguration<SaveTsConfiguration> {

    @Override
    public SaveTsConfiguration getDefaultConfiguration() {
        return null;
    }
}
