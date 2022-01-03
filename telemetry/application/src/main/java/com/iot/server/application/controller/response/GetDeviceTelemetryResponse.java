package com.iot.server.application.controller.response;

import com.iot.server.dao.dto.TsKvDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class GetDeviceTelemetryResponse {
    private List<TsKvDto> kvs;
}
