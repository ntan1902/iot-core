package com.iot.server.transport.service;

import com.iot.server.transport.dto.ValidateDeviceToken;
import com.iot.server.transport.enums.TransportType;

public interface TransportService {
    boolean process(TransportType transportType, ValidateDeviceToken validateDeviceToken);
}
