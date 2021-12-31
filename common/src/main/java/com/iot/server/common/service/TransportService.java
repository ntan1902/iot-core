package com.iot.server.common.service;

import com.iot.server.common.enums.TransportType;
import com.iot.server.common.dto.ValidateDeviceToken;

public interface TransportService {
    void process(TransportType transportType, ValidateDeviceToken validateDeviceToken, String json);
}
