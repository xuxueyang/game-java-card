package core.spring;

import core.core.ReturnResultDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.Serializable;

public class BaseResource implements Serializable{
    protected ResponseEntity<ReturnResultDTO<?>> prepareReturnResult(String returnCode, Object data) {
        return new ResponseEntity<ReturnResultDTO<?>>(new ReturnResultDTO(returnCode, data), HttpStatus.OK);
    }
}
