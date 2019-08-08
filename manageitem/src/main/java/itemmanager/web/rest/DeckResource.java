package itemmanager.web.rest;

import core.core.BaseResource;
import core.core.RequestDTO;
import core.core.ReturnCode;
import core.core.ReturnResultDTO;
import core.dto.acct.dto.UserInfo;
import core.dto.item.dto.CardDTO;
import core.dto.item.dto.EnvoyDTO;
import core.dto.item.dto.RelatedCardDTO;
import core.dto.item.dto.RelatedEnvoyDTO;
import core.protocol.Protocol;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import itemmanager.aop.AcctRpcClient;
import itemmanager.aop.NeedLoginAspect;
import itemmanager.dto.SaveDeckDTO;
import itemmanager.service.AdminService;
import itemmanager.service.CardService;
import itemmanager.service.DeckService;
import itemmanager.service.EnvoyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.websocket.server.PathParam;
import java.util.List;

/**
 * 卡包管理，可以開啓卡包，隨機獲取卡牌。棋子只能通過購買！
 */
@Api(value = "卡組管理", description = "卡組管理")
@RestController
@RequestMapping("/api/deck")
@NeedLoginAspect
public class DeckResource extends ItemBaseResource {
    //解鎖某個用戶所有卡牌和棋子
    @Autowired
    private CardService cardService;
    @Autowired
    private EnvoyService envoyService;

    @Autowired
    private DeckService deckService;

    @GetMapping("/my-cards")
    @ApiOperation(value = "獲取所有卡牌", httpMethod = "GET", response = ResponseEntity.class, notes = "獲取所有卡牌")
    public ReturnResultDTO<?> myCards() {
        try {
            UserInfo info = getUserInfo();
            List<RelatedCardDTO> allByUserId = cardService.findAllByUserId(info.getId());
            return prepareReturnResultDTO(ReturnCode.GET_SUCCESS,allByUserId);
        }catch (Exception e){
            return prepareReturnResultDTO(ReturnCode.ERROR_QUERY,null);
        }
    }
    @GetMapping("/card/{id}")
    @ApiOperation(value = "獲取某个卡牌", httpMethod = "GET", response = ResponseEntity.class, notes = "獲取某个卡牌")
    public ReturnResultDTO<?> getCard(@PathVariable(name = "id") Long id) {
        try {

            return prepareReturnResultDTO(ReturnCode.GET_SUCCESS, cardService.findOne(id));
        }catch (Exception e){
            return prepareReturnResultDTO(ReturnCode.ERROR_QUERY,null);
        }
    }
    @GetMapping("/cards")
    @ApiOperation(value = "獲取所有卡牌", httpMethod = "GET", response = ResponseEntity.class, notes = "獲取所有卡牌")
    public ReturnResultDTO<?> cardlist() {
        try {
            List<CardDTO> all = cardService.findAll();
            return prepareReturnResultDTO(ReturnCode.GET_SUCCESS,all);
        }catch (Exception e){
            return prepareReturnResultDTO(ReturnCode.ERROR_QUERY,null);
        }
    }
    @GetMapping("/my-envoys")
    @ApiOperation(value = "獲取所有棋子", httpMethod = "GET", response = ResponseEntity.class, notes = "獲取所有棋子")
    public ReturnResultDTO<?> myEnvoys() {
        try {
            UserInfo info = getUserInfo();
            List<RelatedEnvoyDTO> allByUserId = envoyService.findAllByUserId(info.getId());
            return prepareReturnResultDTO(ReturnCode.GET_SUCCESS,allByUserId);
        }catch (Exception e){
            return prepareReturnResultDTO(ReturnCode.ERROR_QUERY,null);
        }
    }
    @GetMapping("/envoys/{id}")
    @ApiOperation(value = "獲取某个棋子", httpMethod = "GET", response = ResponseEntity.class, notes = "獲取某个棋子")
    public ReturnResultDTO<?> getEnvoy(@PathVariable(name = "id") Long id) {
        try {
            return prepareReturnResultDTO(ReturnCode.GET_SUCCESS, envoyService.findOne(id));
        }catch (Exception e){
            return prepareReturnResultDTO(ReturnCode.ERROR_QUERY,null);
        }
    }
    @GetMapping("/envoys")
    @ApiOperation(value = "獲取所有棋子", httpMethod = "GET", response = ResponseEntity.class, notes = "獲取所有棋子")
    public ReturnResultDTO<?> envoyslist() {
        try {
            List<EnvoyDTO> all = envoyService.findAll();
            return prepareReturnResultDTO(ReturnCode.GET_SUCCESS,all);
        }catch (Exception e){
            return prepareReturnResultDTO(ReturnCode.ERROR_QUERY,null);
        }
    }
    //最多預設3個卡組
    @GetMapping("get-deck")
    @ApiOperation(value = "查詢", httpMethod = "DELETE", response = ResponseEntity.class, notes = "查詢")
    public ReturnResultDTO<?> getDeck(@RequestParam(name = "deckId",required = false) String deckId) {
        try {
            UserInfo token = getUserInfo();
            return prepareReturnResultDTO(ReturnCode.GET_SUCCESS,deckService.get(token.getId(), deckId));
        } catch (Exception e) {
            return prepareReturnResultDTO(ReturnCode.ERROR_QUERY, null);
        }
    }
    @PostMapping("save-deck")
    @ApiOperation(value = "保存卡組", httpMethod = "POST", response = ResponseEntity.class, notes = "保存卡組")
    public ReturnResultDTO<?> saveDeck(@RequestBody List<SaveDeckDTO> saveDeckDTO){
        try {
            UserInfo token = getUserInfo();
            String deckId = deckService.save(token.getId(), saveDeckDTO);
            if("".equals(deckId))
                return prepareReturnResultDTO(ReturnCode.ERROR_CREATE,null);
            return prepareReturnResultDTO(ReturnCode.CREATE_SUCCESS,deckId);
        }catch (Exception e){
            return prepareReturnResultDTO(ReturnCode.ERROR_CREATE,null);
        }
    }

    //TODO 設置某個卡組為激活狀態
    @PostMapping("deck-set-actived")
    @ApiOperation(value = "保存卡組", httpMethod = "POST", response = ResponseEntity.class, notes = "保存卡組")
    public ReturnResultDTO<?> deckSetActived(@RequestParam(name = "deckId") String deckId){
        try {
            UserInfo token = getUserInfo();
            deckService.deckSetActived(token.getId(), deckId);
            return prepareReturnResultDTO(ReturnCode.UPDATE_SUCCESS,null);
        }catch (Exception e){
            return prepareReturnResultDTO(ReturnCode.ERROR_CREATE,null);
        }
    }
    @DeleteMapping("delete-deck")
    @ApiOperation(value = "刪除卡組", httpMethod = "DELETE", response = ResponseEntity.class, notes = "刪除卡組")
    public ReturnResultDTO<?> saveDeck(@RequestParam(name = "deckId",required = true) String deckId){
        try {
            UserInfo token = getUserInfo();
            deckService.delete(token.getId(),deckId);
            return prepareReturnResultDTO(ReturnCode.DELETE_SUCCESS,null);
        }catch (Exception e){
            return prepareReturnResultDTO(ReturnCode.ERROR_DELETE,null);
        }
    }
}
