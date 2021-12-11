package com.iot.server.application.controller.handler;

import com.iot.server.common.request.EmptyRequest;
import com.nimbusds.jose.jwk.JWKSet;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@RequiredArgsConstructor
@Component
public class GetJwkSetHandler extends BaseHandler<EmptyRequest, Map<String, Object>> {
    private final JWKSet jwkSet;

    @Override
    protected Map<String, Object> processRequest(EmptyRequest emptyRequest) {
        return jwkSet.toJSONObject();
    }
}
