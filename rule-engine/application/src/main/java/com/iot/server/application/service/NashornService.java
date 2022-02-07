package com.iot.server.application.service;

import java.util.UUID;

public interface NashornService {
    UUID eval(String script, String... args);

    Object invokeFunction(UUID scriptId, Object... args);

}
