package core.spring.filter;

import com.google.common.collect.ImmutableMap;
import core.core.ReturnResultDTO;
import core.exception.AccessDeniedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@ControllerAdvice
public class RuntimeExceptionHandler {


    @ExceptionHandler(value = AccessDeniedException.class)
    public ResponseEntity<?> handleValidationException(AccessDeniedException exception, HttpServletRequest request) {
        return new ResponseEntity<>(new ReturnResultDTO<>(exception.getCode(), exception.getReason()), HttpStatus.OK);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<?> handleException(Exception exception, HttpServletRequest request) {
        return prepareResponseEntity("500", exception, request);
    }

    private ResponseEntity<?> prepareResponseEntity(String errorCode, Exception exception, HttpServletRequest request) {
        return new ResponseEntity<>(new ReturnResultDTO<>(errorCode, exception.getMessage()), HttpStatus.OK);
    }
}
