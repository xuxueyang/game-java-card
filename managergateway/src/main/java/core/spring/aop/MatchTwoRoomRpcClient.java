package core.spring.aop;


import core.core.RequestDTO;
import core.core.ReturnResultDTO;
import core.rpc.RoomRPCConstant;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@FeignClient(
        value = RoomRPCConstant.SERVICE_NAME//rpc服务所在的路径（通过spring cloud registry动态获取rpc服务的url地址）
//        ,url = CubeuaaUrl.SERVICE_URL //rpc服务所在的绝对路径，该参数会使'value'失效，
//        ,url = "${user-server-api.url}",
//        fallback = AcctServiceHystrix.class
        ,url = "http://localhost:" + RoomRPCConstant.port
)
@EnableFeignClients
public interface MatchTwoRoomRpcClient {

    @RequestMapping(method = RequestMethod.POST,value = RoomRPCConstant.CREATE_TWO_ROOM)
    ReturnResultDTO CREATE_TWO_ROOM(@RequestBody RequestDTO dto);

    @RequestMapping(method = RequestMethod.POST,value = RoomRPCConstant.SURRENDER)
    ReturnResultDTO SURRENDER(@RequestBody RequestDTO dto);

}

