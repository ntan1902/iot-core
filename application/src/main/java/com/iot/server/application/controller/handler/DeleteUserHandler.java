package com.iot.server.application.controller.handler;

import com.iot.server.application.controller.request.DeleteUserRequest;
import com.iot.server.application.controller.response.DeleteUserResponse;
import com.iot.server.common.exception.IoTException;
import org.springframework.stereotype.Component;

@Component
public class DeleteUserHandler extends BaseHandler<DeleteUserRequest, DeleteUserResponse> {
    @Override
    protected void validate(DeleteUserRequest request) throws IoTException {

    }

    @Override
    protected DeleteUserResponse processRequest(DeleteUserRequest request) {
        DeleteUserResponse response = new DeleteUserResponse();
        response.setSuccess(
                userService.deleteUser(getCurrentUser(), toUUID(request.getUserId()))
        );
        return response;
    }
}
