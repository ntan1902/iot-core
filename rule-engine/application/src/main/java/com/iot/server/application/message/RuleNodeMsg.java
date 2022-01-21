package com.iot.server.application.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RuleNodeMsg {
    private UUID ruleChainId;
    private UUID tenantId;
    private UUID userId;
    private String data;
    private String type;
}
