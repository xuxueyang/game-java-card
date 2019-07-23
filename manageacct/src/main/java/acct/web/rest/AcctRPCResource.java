package acct.web.rest;

import com.googlecode.jsonrpc4j.JsonRpcService;
import com.googlecode.jsonrpc4j.spring.AutoJsonRpcServiceImpl;
import core.core.BaseResource;
import core.core.RequestDTO;
import core.core.ReturnCode;
import io.swagger.annotations.Api;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@AutoJsonRpcServiceImpl
@JsonRpcService("/rpc/acct")
@RequestMapping("/rpc/acct")
@Api(value = "账号登录的rpc调用",description = "账号登录的rpc调用")
public class AcctRPCResource extends BaseResource {


    @RequestMapping(value = "/GET_TOKEN_ID", method = RequestMethod.POST)
    public Map<String,String> getTokenById(RequestDTO requestDTO){
        HashMap<String, String> stringStringHashMap = new HashMap<String, String>();
        stringStringHashMap.put("1","1");
        return stringStringHashMap;
    }

    @RequestMapping(value = "/CHECK_LOGIN", method = RequestMethod.POST)
    public boolean checkToken(RequestDTO requestDTO){
        return true;
    }
    @RequestMapping(value = "/TEST", method = RequestMethod.GET)
    public boolean test(){
        return true;
    }
}
