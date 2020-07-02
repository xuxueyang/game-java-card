package roommanager.web.rest.rpc;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class RoomManagerRpcResource extends BaseResource {
    public static final Logger log = LoggerFactory.getLogger(RoomManagerRpcResource.class);

    @Autowired
    private RoomManagerService roomManagerService;

    @PostMapping("/CREATE_ROOM")
    @ApiOperation(value = "創建房間，根據key", httpMethod = "POST", response = ResponseEntity.class, notes = "創建房間，根據key")
    public ReturnResultDTO CREATE_ROOM(@RequestBody RequestDTO dto) {
        try {
            if(dto.getData() instanceof Map){
                String roomKey = ((Map) dto.getData()).get(RoomRPCConstant.Key.roomKey.name()).toString();
                int areaL = Integer.parseInt(((Map) dto.getData()).get(RoomRPCConstant.Key.areaL.name()).toString());

                JSONArray objects = JSON.parseArray((((Map) dto.getData()).get(RoomRPCConstant.Key.userIds.name())).toString());
                List<Long> userIds = new ArrayList<>(objects.size());
                for (int i = 0; i < objects.size(); i++) {
                    userIds.add(objects.getLong(i));
                }
                String roomId = roomManagerService.CREATE_ROOM(roomKey,areaL,userIds);
                return prepareReturnResultDTO(ReturnCode.CREATE_SUCCESS,roomId);

            }
            return prepareReturnResultDTO(ReturnCode.ERROR_CREATE,"匹配異常，請稍後再試");

        }catch (Exception e){
            log.error(e.getMessage());
            return prepareReturnResultDTO(ReturnCode.ERROR_CREATE,e.getMessage());
        }
    }


    @PostMapping("/two/CREATE_TWO_ROOM")
    @ApiOperation(value = "加入", httpMethod = "POST", response = ResponseEntity.class, notes = "加入")
    public ReturnResultDTO CREATE_TWO_ROOM(@RequestBody RequestDTO dto){
        try {
            if(dto.getData() instanceof Map){
                Byte area = Byte.parseByte(((Map) dto.getData()).get(RoomRPCConstant.Key.areaL.name()).toString());
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


    @PostMapping("/two/CREATE_AUTO_CHESS_ROOM")
    @ApiOperation(value = "加入", httpMethod = "POST", response = ResponseEntity.class, notes = "加入")
    public ReturnResultDTO CREATE_AUTO_CHESS_ROOM(@RequestBody RequestDTO dto){
        try {
            if(dto.getData() instanceof Map){
                int areaL = Integer.parseInt(((Map) dto.getData()).get(RoomRPCConstant.Key.areaL.name()).toString());
                List userIds = (List)(((Map) dto.getData()).get(RoomRPCConstant.Key.userIds.name()));
                roomManagerService.CREATE_AUTO_CHESS_ROOM(areaL,userIds);
                return prepareReturnResultDTO(ReturnCode.CREATE_SUCCESS,true);

            }
            return prepareReturnResultDTO(ReturnCode.ERROR_CREATE,"匹配異常，請稍後再試");

        }catch (Exception e){
            log.error(e.getMessage());
            return prepareReturnResultDTO(ReturnCode.ERROR_CREATE,e.getMessage());
        }
    }
}
