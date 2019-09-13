package itemmanager.web.rest.rpc;

import core.core.BaseResource;
import core.core.RequestDTO;
import core.core.ReturnCode;
import core.core.ReturnResultDTO;
import core.rpc.RoomRPCConstant;
import io.swagger.annotations.ApiOperation;
import itemmanager.service.DeckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController
@RequestMapping("/api/netty.rpc/deck")
public class DeckRpcResource extends BaseResource {
    @Autowired
    private DeckService deckService;

    @PostMapping("/GET_ACTIVED_DECK")
    @ApiOperation(value = "得到卡組", httpMethod = "POST", response = ResponseEntity.class, notes = "得到卡組")
    public ReturnResultDTO CREATE_TWO_ROOM(@RequestBody RequestDTO dto) {
        try {
            if(dto.getData() instanceof Map){
                String userId = ((Map) dto.getData()).get(RoomRPCConstant.Key.userId.name()).toString();
                String deckId =  deckService.findActivedDeckByUserId(Long.parseLong(userId));
                if(deckId==null){
                    throw new Exception("卡组不存在");
                }
                return prepareReturnResultDTO(ReturnCode.GET_SUCCESS,deckService.getDeckConfigById(deckId));

            }
            return prepareReturnResultDTO(ReturnCode.ERROR_QUERY, "data格式错误");
        } catch (Exception e) {
            return prepareReturnResultDTO(ReturnCode.ERROR_QUERY, e.getMessage());
        }
    }
}