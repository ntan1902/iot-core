package com.iot.server.application.controller.handler;

import com.iot.server.application.controller.request.GetDeviceTelemetryRequest;
import com.iot.server.application.controller.response.GetDeviceTelemetryResponse;
import com.iot.server.common.exception.IoTException;
import com.iot.server.dao.dto.TsKvDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GetDeviceTelemetryHandler extends BaseHandler<GetDeviceTelemetryRequest, GetDeviceTelemetryResponse>{
    @Override
    protected void validate(GetDeviceTelemetryRequest request) throws IoTException {
        validateNotEmpty("deviceId", request.getDeviceId());
    }

    @Override
    protected GetDeviceTelemetryResponse processRequest(GetDeviceTelemetryRequest request) {
        GetDeviceTelemetryResponse response = new GetDeviceTelemetryResponse();

        List<TsKvDto> kvs = tsKvService.findTsKvByEntityId(toUUID(request.getDeviceId()));

        response.setKvs(kvs);
        return response;
    }
}
