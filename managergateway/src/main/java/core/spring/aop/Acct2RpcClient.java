package core.spring.aop;


import core.rpc.AcctRPCConstant;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;



public interface Acct2RpcClient {
    @RequestMapping(method = RequestMethod.GET,value = AcctRPCConstant.GET_TOKEN_ID)
    Map<String,String> getTokenById(@RequestParam("id") String id);

    @RequestMapping(method = RequestMethod.GET,value = AcctRPCConstant.CHECK_LOGIN)
    boolean checkToken(@RequestParam("token") String token);

    @RequestMapping(method = RequestMethod.GET,value = AcctRPCConstant.TEST)
    boolean test();
}

