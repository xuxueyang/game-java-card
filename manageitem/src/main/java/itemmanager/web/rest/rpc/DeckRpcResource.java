package itemmanager.web.rest.rpc;

import core.core.BaseResource;
import core.core.RequestDTO;
import core.core.ReturnCode;
import core.core.ReturnResultDTO;
import core.rpc.MatchRPCConstant;
import io.swagger.annotations.ApiOperation;
import itemmanager.service.DeckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController("/api/rpc/deck")
public class DeckRpcResource extends BaseResource {
    @Autowired
    private DeckService deckService;

    @PostMapping("/GET_DECK")
    @ApiOperation(value = "加入", httpMethod = "POST", response = ResponseEntity.class, notes = "加入")
    public ReturnResultDTO CREATE_TWO_ROOM(@RequestBody RequestDTO dto) {
        try {
            String deckId = dto.getData().toString();
            return prepareReturnResultDTO(ReturnCode.GET_SUCCESS,deckService.getDeckConfigById(deckId));
        } catch (Exception e) {
            return prepareReturnResultDTO(ReturnCode.ERROR_QUERY, null);
        }
    }
}