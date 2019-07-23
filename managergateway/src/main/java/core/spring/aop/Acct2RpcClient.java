package core.spring.aop;


import core.core.RequestDTO;
import core.rpc.AcctRPCConstant;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;



public interface Acct2RpcClient {
    @RequestMapping(method = RequestMethod.POST,value = AcctRPCConstant.GET_TOKEN_ID)
    Map<String,String> getTokenById(@RequestBody RequestDTO requestDTO);

    @RequestMapping(method = RequestMethod.POST,value = AcctRPCConstant.CHECK_LOGIN)
    boolean checkToken(@RequestBody RequestDTO requestDTO);

    @RequestMapping(method = RequestMethod.POST,value = AcctRPCConstant.TEST)
    boolean test();
}

