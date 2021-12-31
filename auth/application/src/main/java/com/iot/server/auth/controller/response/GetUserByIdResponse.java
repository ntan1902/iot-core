package com.iot.server.auth.controller.response;

import com.iot.server.dao.dto.UserDto;
import lombok.Data;

@Data
public class GetUserByIdResponse {
    private UserDto user;
}
