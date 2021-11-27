package com.iot.server.common.exception;

import com.iot.server.common.enums.ReasonEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IoTException extends RuntimeException {

   private final ReasonEnum reason;

   protected IoTException(ReasonEnum reason, String message) {
      super(message);
      this.reason = reason;
   }
}