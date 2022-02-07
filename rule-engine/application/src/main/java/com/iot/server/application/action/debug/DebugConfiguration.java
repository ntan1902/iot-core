package com.iot.server.application.action.debug;

import com.iot.server.application.action.ActionConfiguration;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DebugConfiguration implements ActionConfiguration<DebugConfiguration> {
    private String script;

    @Override
    public DebugConfiguration getDefaultConfiguration() {
        return DebugConfiguration.builder()
                .script(
                        "return JSON.stringify(msg);"
                )
                .build();
    }
}