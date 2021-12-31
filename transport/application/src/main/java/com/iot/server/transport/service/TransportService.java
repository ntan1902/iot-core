package com.iot.server.transport.service;

import com.iot.server.common.enums.TransportType;
import com.iot.server.transport.dto.ValidateDeviceToken;

public interface TransportService {
    void process(TransportType transportType, ValidateDeviceToken validateDeviceToken, String json);
}
