package core.spring.web.rest;


import core.core.BaseResource;
import core.core.ReturnCode;
import core.core.ReturnResultDTO;
import core.rpc.AcctRPCConstant;
import core.rpc.DeckRPCConstant;
import core.rpc.RoomRPCConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/api/gateway")
public class GatewayResource extends BaseResource{
    Map<String,String> serverList = new HashMap<>();
    {
        serverList.put(AcctRPCConstant.SERVICE_NAME, AcctRPCConstant.port);
        serverList.put(DeckRPCConstant.SERVICE_NAME, DeckRPCConstant.port);
        serverList.put(RoomRPCConstant.SERVICE_NAME, RoomRPCConstant.port);

    }

    private static Logger logger = LoggerFactory.getLogger(GatewayResource.class);
    @GetMapping("/server-port")
    public ReturnResultDTO getServerList(){
        try {
            return prepareReturnResultDTO(ReturnCode.GET_SUCCESS,serverList);
        }catch (Exception e){
            logger.error(e.getMessage());
            return prepareReturnResultDTO(ReturnCode.ERROR_QUERY,null);
        }
    }
//    @GetMapping("/server-port")
    @RequestMapping(value = "/server-port2",method = RequestMethod.GET)
    public ReturnResultDTO getServerList2(){
        try {
            return prepareReturnResultDTO(ReturnCode.GET_SUCCESS,serverList);
        }catch (Exception e){
            logger.error(e.getMessage());
            return prepareReturnResultDTO(ReturnCode.ERROR_QUERY,null);
        }
    }
}
