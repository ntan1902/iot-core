package com.iot.server.application.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AlarmModel {
    private UUID deviceId;
    private String name;
    private String severity;
    private String detail;
}
