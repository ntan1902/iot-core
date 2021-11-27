package com.iot.server.application.exception;

import com.iot.server.common.enums.ReasonEnum;
import com.iot.server.common.exception.IoTException;
import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Log4j2
@RestControllerAdvice
public class IoTExceptionHandler extends ResponseEntityExceptionHandler implements AccessDeniedHandler {

   private static final Map<ReasonEnum, HttpStatus> reasonToStatusMap = new HashMap<>();

   static {
      reasonToStatusMap.put(ReasonEnum.UNDEFINED, HttpStatus.INTERNAL_SERVER_ERROR);
      reasonToStatusMap.put(ReasonEnum.INVALID_PARAMS, HttpStatus.BAD_REQUEST);
      reasonToStatusMap.put(ReasonEnum.AUTHENTICATION_FAILED, HttpStatus.UNAUTHORIZED);
      reasonToStatusMap.put(ReasonEnum.AUTHORIZATION_FAILED, HttpStatus.UNAUTHORIZED);
      reasonToStatusMap.put(ReasonEnum.PERMISSION_DENIED, HttpStatus.FORBIDDEN);
   }

   private static HttpStatus reasonToStatus(ReasonEnum reasonEnum) {
      return reasonToStatusMap.getOrDefault(reasonEnum, HttpStatus.INTERNAL_SERVER_ERROR);
   }

   private ResponseEntity<Object> getResponse(HttpStatus httpStatus, String message) {
      // 1. Create payload containing exception details.
      IoTExceptionResponse iotExceptionResponse = new IoTExceptionResponse(
          message,
          httpStatus,
          ZonedDateTime.now(ZoneId.of("Z"))
      );

      // 2. Return response entity
      return new ResponseEntity<>(iotExceptionResponse, httpStatus);
   }

   @ExceptionHandler(value = {IoTException.class})
   public ResponseEntity<Object> handleIoTException(IoTException exception) {
      log.error(exception.getMessage());
      return getResponse(reasonToStatus(exception.getReason()), exception.getMessage());

   }

   @Override
   public void handle(HttpServletRequest request, HttpServletResponse response,
       AccessDeniedException accessDeniedException) throws IOException, ServletException {
   }
}
