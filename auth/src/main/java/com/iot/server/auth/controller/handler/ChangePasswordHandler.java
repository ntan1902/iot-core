package com.iot.server.auth.controller.handler;

import com.iot.server.auth.controller.request.ChangePasswordRequest;
import com.iot.server.auth.controller.response.ChangePasswordResponse;
import com.iot.server.common.exception.IoTException;
import org.springframework.stereotype.Component;

@Component
public class ChangePasswordHandler extends BaseHandler<ChangePasswordRequest, ChangePasswordResponse> {
    @Override
    protected void validate(ChangePasswordRequest request) throws IoTException {

    }

    @Override
    protected ChangePasswordResponse processRequest(ChangePasswordRequest request) {
        ChangePasswordResponse response = new ChangePasswordResponse();

        response.setSuccess(
                userService.changePassword(request.getCurrentPassword(),
                                request.getNewPassword())
        );

        return response;
    }
}