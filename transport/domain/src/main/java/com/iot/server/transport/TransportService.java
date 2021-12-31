package com.iot.server.transport;

import com.iot.server.common.enums.TransportType;
import com.iot.server.transport.model.ValidateDeviceToken;

public interface TransportService {
    void process(TransportType transportType, ValidateDeviceToken validateDeviceToken, String json);
}
