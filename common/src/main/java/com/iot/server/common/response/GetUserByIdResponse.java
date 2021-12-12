package com.iot.server.common.response;

import com.iot.server.common.dto.UserDto;
import lombok.Data;

@Data
public class GetUserByIdResponse {
    private UserDto user;
}
