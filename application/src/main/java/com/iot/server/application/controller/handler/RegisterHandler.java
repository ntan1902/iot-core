package com.iot.server.application.controller.handler;

import com.iot.server.application.controller.request.RegisterRequest;
import com.iot.server.application.controller.response.RegisterResponse;
import com.iot.server.common.dto.UserDto;
import com.iot.server.common.exception.IoTException;
import org.springframework.stereotype.Component;

@Component
public class RegisterHandler extends BaseHandler<RegisterRequest, RegisterResponse> {
    @Override
    protected void validate(RegisterRequest request) throws IoTException {
        validateNotEmpty("email", request.getEmail());
        validateNotEmpty("firstName", request.getFirstName());
        validateNotEmpty("lastName", request.getLastName());
        validateNotEmpty("password", request.getPassword());
    }

    @Override
    protected RegisterResponse processRequest(RegisterRequest request) {
        UserDto user = userService.registerUser(getUserDto(request), request.getPassword());

        RegisterResponse response = new RegisterResponse();
        response.setUserId(user.getId());
        return response;
    }

    private UserDto getUserDto(RegisterRequest request) {
        return UserDto.builder()
                .email(request.getEmail())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .build();
    }
}
