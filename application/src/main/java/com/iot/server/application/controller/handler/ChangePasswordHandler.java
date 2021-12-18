package com.iot.server.application.controller.handler;

import com.iot.server.application.controller.request.ChangePasswordRequest;
import com.iot.server.application.controller.response.ChangePasswordResponse;
import com.iot.server.common.exception.IoTException;
import com.iot.server.common.model.SecurityUser;
import org.springframework.stereotype.Component;

@Component
public class ChangePasswordHandler extends BaseHandler<ChangePasswordRequest, ChangePasswordResponse> {
    @Override
    protected void validate(ChangePasswordRequest request) throws IoTException {

    }

    @Override
    protected ChangePasswordResponse processRequest(ChangePasswordRequest request) {
        ChangePasswordResponse response = new ChangePasswordResponse();

        SecurityUser currentUser = getCurrentUser();
        response.setSuccess(
                userService.changePassword(currentUser.getId(),
                        request.getCurrentPassword(),
                        request.getNewPassword()));

        return response;
    }
}
