package com.iot.server.common.enums;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ReasonEnum {
   UNDEFINED(0, "Undefined"),
   INVALID_PARAMS(1, "Params is invalid"),
   AUTHENTICATION_FAILED(2, "Authentication failed"),
   AUTHORIZATION_FAILED(3, "Authorization failed"),
   PERMISSION_DENIED(4, "Permission denied"),
   EXPIRED_TOKEN(5, "Expired token");

   private static final Map<Integer, ReasonEnum> MAP =
       Arrays.stream(ReasonEnum.values())
           .collect(Collectors.toMap(ReasonEnum::getValue, Function.identity()));

   private final int value;
   private final String message;
}
