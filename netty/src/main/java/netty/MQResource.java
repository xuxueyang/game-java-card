package netty;

import com.codahale.metrics.annotation.Timed;
import core.core.BaseResource;
import core.core.RequestDTO;
import core.core.ReturnCode;
import core.core.ReturnResultDTO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import netty.rpc.AcctRpcClient;

//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;


@Deprecated
@RestController
public class MQResource extends BaseResource {

    @Autowired
    private AcctRpcClient acctRpcClient;

    @Timed
    @RequestMapping( {"/post"})
    @ApiOperation(value = "传输消息", httpMethod = "POST", response = ReturnResultDTO.class)
    @ApiResponses({@io.swagger.annotations.ApiResponse(code = 200, message = "成功")})
    public ReturnResultDTO<?> post(@RequestBody RequestDTO json) throws Exception {

        return prepareReturnResultDTO(ReturnCode.CREATE_SUCCESS, null);
    }
    @Timed
    @RequestMapping({"/get"})
    @ApiOperation(value = "查询调戏", httpMethod = "POST", response = ReturnResultDTO.class)
    @ApiResponses({@io.swagger.annotations.ApiResponse(code = 200, message = "成功")})
    public ReturnResultDTO<?> get() throws Exception {
        RequestDTO dto = new RequestDTO();
        dto.setData(System.currentTimeMillis());
        return prepareReturnResultDTO(ReturnCode.GET_SUCCESS, null);
    }
}
