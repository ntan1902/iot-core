package com.iot.server.application.controller.handler;

import com.iot.server.application.controller.request.CreateUserRequest;
import com.iot.server.application.controller.response.CreateUserResponse;
import com.iot.server.common.exception.IoTException;
import com.iot.server.dao.dto.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CreateUserHandler extends BaseHandler<CreateUserRequest, CreateUserResponse> {

    @Override
    protected void validate(CreateUserRequest request) throws IoTException {
        validateNotNull("authorities", request.getAuthorities());
        validateAuthorities("authorities", request.getAuthorities());
    }

    @Override
    protected CreateUserResponse processRequest(final CreateUserRequest request) {
        final CreateUserResponse response = new CreateUserResponse();

        final UserDto user = getUserFromRequest(request);
        final UserDto savedUser = userService.createUserWithAuthorities(user, request.getAuthorities());
        response.setUserId(savedUser.getId());

        return response;
    }

    private UserDto getUserFromRequest(CreateUserRequest request) {
        UserDto userDto = UserDto.builder()
                .email(request.getEmail())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .tenantId(toUUID(request.getTenantId()))
                .customerId(toUUID(request.getCustomerId()))
                .build();

        if (!request.getTenantId().isEmpty())
            userDto.setTenantId(toUUID(request.getTenantId()));

        if (!request.getCustomerId().isEmpty())
            userDto.setCustomerId(toUUID(request.getCustomerId()));

        return userDto;
    }
}
