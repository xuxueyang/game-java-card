package core.spring.web.rest.rpc;

import core.core.BaseResource;
import core.core.RequestDTO;
import core.core.ReturnCode;
import core.core.ReturnResultDTO;
import core.rpc.MatchRPCConstant;
import core.spring.service.room.RoomManagerService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController("/api/rpc/room")
public class RoomManagerRpcResource extends BaseResource {
    @Autowired
    private RoomManagerService roomManagerService;

    @PostMapping("/two/CREATE_TWO_ROOM")
    @ApiOperation(value = "加入", httpMethod = "POST", response = ResponseEntity.class, notes = "加入")
    public ReturnResultDTO CREATE_TWO_ROOM(@RequestBody RequestDTO dto){
        try {
            if(dto.getData() instanceof Map){
                Long oneUserId = Long.parseLong(((Map) dto.getData()).get(MatchRPCConstant.Key.oneUser.name()).toString());
                Long twoUserId = Long.parseLong(((Map) dto.getData()).get(MatchRPCConstant.Key.twoUser.name()).toString());
                roomManagerService.CREATE_TWO_ROOM(oneUserId,twoUserId);
                return prepareReturnResultDTO(ReturnCode.CREATE_SUCCESS,true);

            }
            return prepareReturnResultDTO(ReturnCode.ERROR_CREATE,"匹配異常，請稍後再試");

        }catch (Exception e){
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
}
