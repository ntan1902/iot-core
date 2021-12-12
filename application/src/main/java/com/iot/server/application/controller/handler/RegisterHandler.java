package com.iot.server.application.controller.handler;

import com.iot.server.common.dto.UserDto;
import com.iot.server.common.exception.IoTException;
import com.iot.server.common.request.RegisterRequest;
import com.iot.server.common.response.RegisterResponse;
import com.iot.server.common.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class RegisterHandler extends BaseHandler<RegisterRequest, RegisterResponse> {
    private final UserService userService;

    @Override
    protected void validate(RegisterRequest request) throws IoTException {
        validateNotEmpty("email", request.getEmail());
        validateNotEmpty("firstName", request.getFirstName());
        validateNotEmpty("lastName", request.getLastName());
        validateNotEmpty("password", request.getPassword());
    }

    @Override
    protected RegisterResponse processRequest(RegisterRequest request) {
        UserDto user = userService.registerUser(request);

        RegisterResponse response = new RegisterResponse();
        response.setSuccess(user != null);
        return response;
    }
}
