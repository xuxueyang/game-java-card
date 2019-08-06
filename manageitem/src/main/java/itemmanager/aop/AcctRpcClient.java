package itemmanager.aop;


import core.core.RequestDTO;
import core.core.ReturnResultDTO;
import core.rpc.AcctRPCConstant;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@FeignClient(
        value = AcctRPCConstant.SERVICE_NAME//rpc服务所在的路径（通过spring cloud registry动态获取rpc服务的url地址）
//        ,url = CubeuaaUrl.SERVICE_URL //rpc服务所在的绝对路径，该参数会使'value'失效，
//        ,url = "${user-server-api.url}",
//        fallback = AcctServiceHystrix.class
        ,url = "http://localhost:20007"
)
@EnableFeignClients
public interface AcctRpcClient {
//    @RequestMapping(method = RequestMethod.POST,value = AcctRPCConstant.GET_TOKEN_ID)
//    ReturnResultDTO getTokenById(@RequestBody RequestDTO dto);

    @RequestMapping(method = RequestMethod.POST,value = AcctRPCConstant.CHECK_LOGIN)
    ReturnResultDTO checkToken(@RequestBody RequestDTO dto);

    @RequestMapping(method = RequestMethod.POST,value = AcctRPCConstant.GET_USER_INFO)
    ReturnResultDTO getUserInfoByToken(@RequestBody RequestDTO dto);
//
//    @RequestMapping(method = RequestMethod.GET,value = AcctRPCConstant.TEST)
//    ReturnResultDTO test();
}

