package com.iot.server.application.controller.response;

import com.iot.server.dao.dto.TsKvDto;
import lombok.Data;

import java.util.List;

@Data
public class GetDeviceLatestTelemetryResponse {
    private List<TsKvDto> kvs;
}
