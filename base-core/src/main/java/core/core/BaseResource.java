package core.core;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.Serializable;

public class BaseResource<T> implements Serializable {
//    protected ResponseEntity<ReturnResultDTO<?>> prepareResponseEntity(String returnCode, Object data) {
//        return new ResponseEntity(new ReturnResultDTO(returnCode, data), HttpStatus.OK);
//    }
    protected ReturnResultDTO<?> prepareReturnResultDTO(String returnCode, Object data) {
        return new ReturnResultDTO(returnCode, data);
    }
}
