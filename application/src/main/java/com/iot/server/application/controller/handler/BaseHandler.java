package com.iot.server.application.controller.handler;

import com.iot.server.common.enums.ReasonEnum;
import com.iot.server.common.exception.IoTException;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.util.UUID;

@Slf4j
public abstract class BaseHandler<T, U> {
    protected abstract void validate(T request) throws IoTException;

    protected abstract U processRequest(T request);

    public U handleRequest(T request) {
        log.info("request={}", request);
        val response = processRequest(request);
        log.info("response={}", response);
        return response;
    }

    protected void validateNotEmpty(String field, String value) {
        validateCondition(value.isEmpty(), field + " should not be empty");
    }

    protected void validateNotEqualsZero(String field, int value) {
        validateCondition(value == 0, field + " is invalid");
    }

    protected void validateNotEqualsZero(String field, long value) {
        validateCondition(value == 0L, field + " is invalid");
    }

    private void validateCondition(boolean conditionToFail, String messageIfFailed) {
        if (conditionToFail) {
            throw new IoTException(ReasonEnum.INVALID_PARAMS, messageIfFailed);
        }
    }

    protected UUID toUUID(String id) {
        return UUID.fromString(id);
    }
}


