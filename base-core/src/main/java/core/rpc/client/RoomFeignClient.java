package core.rpc.client;


import core.core.RequestDTO;
import core.core.ReturnResultDTO;
import core.rpc.FeignConstants;
import core.rpc.RoomRPCConstant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = FeignConstants.SERVICE_ROOM)
public interface RoomFeignClient {

    @RequestMapping(method = RequestMethod.POST,value = "/api/CREATE_ROOM")
    ReturnResultDTO CREATE_ROOM(@RequestBody RequestDTO dto);

    @RequestMapping(method = RequestMethod.POST,value = "/api/two/CREATE_TWO_ROOM")
    ReturnResultDTO CREATE_TWO_ROOM(@RequestBody RequestDTO dto);

    @RequestMapping(method = RequestMethod.POST,value = "/api/two/SURRENDER")
    ReturnResultDTO SURRENDER(@RequestBody RequestDTO dto);

}
