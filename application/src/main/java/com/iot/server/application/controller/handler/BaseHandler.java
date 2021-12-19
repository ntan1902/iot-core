package com.iot.server.application.controller.handler;

import com.iot.server.application.controller.request.CreateUserRequest;
import com.iot.server.common.dto.UserDto;
import com.iot.server.common.enums.AuthorityEnum;
import com.iot.server.common.enums.ReasonEnum;
import com.iot.server.common.exception.IoTException;
import com.iot.server.common.service.UserService;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

@Slf4j
public abstract class BaseHandler<T, U> {
    @Autowired
    protected UserService userService;

    protected abstract void validate(final T request) throws IoTException;

    protected abstract U processRequest(final T request);

    public U handleRequest(T request) {
        log.info("request={}", request);

        validate(request);

        val response = processRequest(request);
        log.info("response={}", response);
        return response;
    }

    protected void validateNotEmpty(String field, String value) {
        validateCondition(value.isEmpty(), field + " should not be empty");
    }

    protected void validateNotNull(String field, Object value) {
        validateCondition(value == null, field + " should not be null");
    }

    protected void validateNotEqualsZero(String field, int value) {
        validateCondition(value == 0, field + " is invalid");
    }

    protected void validateNotEqualsZero(String field, long value) {
        validateCondition(value == 0L, field + " is invalid");
    }

    protected void validateAuthorities(String field, List<String> authorities) {
        authorities.forEach(authority -> {
            AuthorityEnum authorityEnum = AuthorityEnum.getAuthority(authority);
            validateCondition(authorityEnum == null, field + " is invalid");
        });
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


