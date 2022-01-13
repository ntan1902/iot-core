package com.iot.server.application.controller.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetRuleChainsRequest {
    private int page;
    private int size;
    private String order;
    private String property;
}
