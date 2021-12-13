package com.iot.server.application.controller.handler;

import com.iot.server.common.dto.UserDto;
import com.iot.server.common.exception.IoTException;
import com.iot.server.application.controller.request.GetUserByIdRequest;
import com.iot.server.application.controller.response.GetUserByIdResponse;
import com.iot.server.common.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class GetUserByIdHandler extends BaseHandler<GetUserByIdRequest, GetUserByIdResponse> {

    private final UserService userService;

    @Override
    protected void validate(GetUserByIdRequest request) throws IoTException {
        validateNotEmpty("userId", request.getUserId());
    }

    @Override
    protected GetUserByIdResponse processRequest(GetUserByIdRequest request) {
        GetUserByIdResponse response = new GetUserByIdResponse();

        UserDto user = userService.findUserWithExtraInfoById(toUUID(request.getUserId()));
        response.setUser(user);

        return response;
    }
}
