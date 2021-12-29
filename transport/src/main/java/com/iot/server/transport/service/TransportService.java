package com.iot.server.transport.service;

import com.iot.server.transport.dto.ValidateDeviceToken;
import com.iot.server.common.enums.TransportType;

public interface TransportService {
    void process(TransportType transportType, ValidateDeviceToken validateDeviceToken);
}
