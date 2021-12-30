package com.iot.server.common.response;

import com.google.gson.JsonElement;
import lombok.Data;

import java.util.UUID;

@Data
public class DeviceResponse {
    private UUID id;
    private UUID tenantId;
    private UUID customerId;
    private JsonElement deviceData;
    private String type;
    private String name;
    private String label;
    private UUID firmwareId;
    private UUID softwareId;
}