package com.iot.server.application.controller.handler;

import com.iot.server.application.controller.request.CreateUserRequest;
import com.iot.server.application.controller.response.CreateUserResponse;
import com.iot.server.common.dto.UserDto;
import com.iot.server.common.exception.IoTException;
import com.iot.server.common.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Slf4j
@Component
public class CreateUserHandler extends BaseHandler<CreateUserRequest, CreateUserResponse> {
    private final UserService userService;

    @Override
    protected void validate(CreateUserRequest request) throws IoTException {
        validateNotNull("authorities", request.getAuthorities());
        validateAuthorities("authorities", request.getAuthorities());
    }

    @Override
    protected CreateUserResponse processRequest(CreateUserRequest request) {
        CreateUserResponse response = new CreateUserResponse();
        UserDto user =
                userService.saveUserWithAuthorities(
                        UserDto.builder()
                                .email(request.getEmail())
                                .firstName(request.getFirstName())
                                .lastName(request.getLastName())
                                .createUid(getCurrentUser().getId())
                                .build(),
                        request.getAuthorities());
        response.setUserId(user.getId());
        return response;
    }
}
