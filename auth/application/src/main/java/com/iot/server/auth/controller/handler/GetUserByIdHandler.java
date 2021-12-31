package com.iot.server.auth.controller.handler;

import com.iot.server.auth.controller.request.GetUserByIdRequest;
import com.iot.server.auth.controller.response.GetUserByIdResponse;
import com.iot.server.common.dto.UserDto;
import com.iot.server.common.exception.IoTException;
import org.springframework.stereotype.Component;

@Component
public class GetUserByIdHandler extends BaseHandler<GetUserByIdRequest, GetUserByIdResponse> {
    @Override
    protected void validate(final GetUserByIdRequest request) throws IoTException {
        validateNotEmpty("userId", request.getUserId());
    }

    @Override
    protected GetUserByIdResponse processRequest(final GetUserByIdRequest request) {
        final GetUserByIdResponse response = new GetUserByIdResponse();

        final UserDto user = userService.findUserWithExtraInfoById(toUUID(request.getUserId()));
        response.setUser(user);

        return response;
    }
}
