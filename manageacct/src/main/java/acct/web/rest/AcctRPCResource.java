package acct.web.rest;

import com.googlecode.jsonrpc4j.JsonRpcService;
import com.googlecode.jsonrpc4j.spring.AutoJsonRpcServiceImpl;
import core.core.BaseResource;
import core.core.RequestDTO;
import core.core.ReturnCode;
import core.core.ReturnResultDTO;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;
import core.dto.acct.dto.UserInfo;
import java.util.HashMap;

@RestController
@AutoJsonRpcServiceImpl
@JsonRpcService("/rpc/acct")
@RequestMapping("/rpc/acct")
@Api(value = "账号登录的rpc调用",description = "账号登录的rpc调用")
public class AcctRPCResource extends BaseResource {


    @RequestMapping(value = "/GET_TOKEN_ID", method = RequestMethod.POST)
    public ReturnResultDTO getTokenById(@RequestBody RequestDTO dto){
        HashMap<String, String> stringStringHashMap = new HashMap<String, String>();
        stringStringHashMap.put("1","1");
        return prepareReturnResultDTO(ReturnCode.GET_SUCCESS,stringStringHashMap);
    }

    @RequestMapping(value = "/CHECK_LOGIN", method = RequestMethod.POST)
    public ReturnResultDTO checkToken(@RequestBody  RequestDTO dto){
        return prepareReturnResultDTO(ReturnCode.GET_SUCCESS,true);
    }
    @RequestMapping(value = "/GET_USER_INFO", method = RequestMethod.POST)
    public ReturnResultDTO getUserInfoByToken(@RequestBody  RequestDTO dto){
        UserInfo info = new UserInfo();
        return prepareReturnResultDTO(ReturnCode.GET_SUCCESS,info );
    }

    @RequestMapping(value = "/TEST", method = RequestMethod.GET)
    public ReturnResultDTO test(){
        return prepareReturnResultDTO(ReturnCode.GET_SUCCESS,null);
    }
}
