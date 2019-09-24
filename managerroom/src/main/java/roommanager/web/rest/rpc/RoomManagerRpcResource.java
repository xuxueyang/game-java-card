package roommanager.web.rest.rpc;

import core.core.BaseResource;
import core.core.RequestDTO;
import core.core.ReturnCode;
import core.core.ReturnResultDTO;
import core.rpc.RoomRPCConstant;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roommanager.service.room.RoomManagerService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/netty.rpc/room")
public class RoomManagerRpcResource extends BaseResource {
    public static final Logger log = LoggerFactory.getLogger(RoomManagerRpcResource.class);

    @PostMapping("/two/CREATE_TWO_ROOM")
    @ApiOperation(value = "加入", httpMethod = "POST", response = ResponseEntity.class, notes = "加入")
    public ReturnResultDTO CREATE_TWO_ROOM(@RequestBody RequestDTO dto){
        try {
            if(dto.getData() instanceof Map){
                Byte area = Byte.parseByte(((Map) dto.getData()).get(RoomRPCConstant.Key.area.name()).toString());
                Long oneUserId = Long.parseLong(((Map) dto.getData()).get(RoomRPCConstant.Key.oneUser.name()).toString());
                Long twoUserId = Long.parseLong(((Map) dto.getData()).get(RoomRPCConstant.Key.twoUser.name()).toString());
                roomManagerService.CREATE_TWO_ROOM(oneUserId,twoUserId,area);
                return prepareReturnResultDTO(ReturnCode.CREATE_SUCCESS,true);

            }
            return prepareReturnResultDTO(ReturnCode.ERROR_CREATE,"匹配異常，請稍後再試");

        }catch (Exception e){
            log.error(e.getMessage());
            return prepareReturnResultDTO(ReturnCode.ERROR_CREATE,e.getMessage());
        }
    }
    @PostMapping("/two/SURRENDER")
    @ApiOperation(value = "投降", httpMethod = "POST", response = ResponseEntity.class, notes = "投降")
    public ReturnResultDTO SURRENDER(@RequestBody RequestDTO dto){
        try {
            return prepareReturnResultDTO(ReturnCode.ERROR_CREATE,"匹配異常，請稍後再試");
        }catch (Exception e){
            return prepareReturnResultDTO(ReturnCode.ERROR_CREATE,e.getMessage());
        }
    }

    @Autowired
    private RoomManagerService roomManagerService;

    @PostMapping("/two/CREATE_AUTO_CHESS_ROOM")
    @ApiOperation(value = "加入", httpMethod = "POST", response = ResponseEntity.class, notes = "加入")
    public ReturnResultDTO CREATE_AUTO_CHESS_ROOM(@RequestBody RequestDTO dto){
        try {
            if(dto.getData() instanceof Map){
                Byte area = Byte.parseByte(((Map) dto.getData()).get(RoomRPCConstant.Key.area.name()).toString());
                List userIds = (List)(((Map) dto.getData()).get(RoomRPCConstant.Key.userIds.name()));
                roomManagerService.CREATE_AUTO_CHESS_ROOM(userIds,area);
                return prepareReturnResultDTO(ReturnCode.CREATE_SUCCESS,true);

            }
            return prepareReturnResultDTO(ReturnCode.ERROR_CREATE,"匹配異常，請稍後再試");

        }catch (Exception e){
            log.error(e.getMessage());
            return prepareReturnResultDTO(ReturnCode.ERROR_CREATE,e.getMessage());
        }
    }
}
