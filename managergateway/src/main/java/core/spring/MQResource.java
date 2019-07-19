package core.spring;

import core.core.RequestDTO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import core.core.ReturnCode;
import core.core.ReturnResultDTO;
import core.inter.MQFirstInterface;
import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.codahale.metrics.annotation.Timed;


@RestController
public class MQResource extends BaseResource{
//    public static Concurrent
    private static SpringMQFirstService queue = new SpringMQFirstService();
    public static MQFirstInterface getQueue(){
        return queue;
    }

    @Timed
    @RequestMapping( {"/post"})
    @ApiOperation(value = "传输消息", httpMethod = "POST", response = ReturnResultDTO.class)
    @ApiResponses({@io.swagger.annotations.ApiResponse(code = 200, message = "成功")})
    public ResponseEntity<?> post(@RequestBody RequestDTO json) throws Exception {

        return prepareReturnResult(ReturnCode.CREATE_SUCCESS, null);
    }
    @Timed
    @RequestMapping({"/get"})
    @ApiOperation(value = "查询调戏", httpMethod = "POST", response = ReturnResultDTO.class)
    @ApiResponses({@io.swagger.annotations.ApiResponse(code = 200, message = "成功")})
    public ResponseEntity<?> get() throws Exception {
        RequestDTO dto = new RequestDTO();
        dto.setData(System.currentTimeMillis());
        queue.putObjectByType(new Byte("1"), dto);
        return prepareReturnResult(ReturnCode.GET_SUCCESS, null);
    }
}
