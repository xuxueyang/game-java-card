package itemmanager.web.rest;

import core.core.BaseResource;
import core.core.ReturnCode;
import core.core.ReturnResultDTO;
import core.dto.acct.dto.UserInfo;
import core.dto.item.dto.CardDTO;
import core.dto.item.dto.EnvoyDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import itemmanager.aop.NeedLoginAspect;
import itemmanager.dto.AdminUpdateCardDTO;
import itemmanager.dto.AdminUpdateEnvoyDTO;
import itemmanager.service.AdminService;
import itemmanager.service.CardService;
import itemmanager.service.EnvoyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 卡包管理，可以開啓卡包，隨機獲取卡牌。棋子只能通過購買！
 */
@Api(value = "管理權限", description = "管理權限")
@RestController
@RequestMapping(value = "/api/admin", produces="application/json;charset=utf-8")
//@NeedLoginAspect
public class AdminResource extends ItemBaseResource {
    //解鎖某個用戶所有卡牌和棋子
    @Autowired
    private AdminService adminService;
    @Autowired
    private CardService cardService;
    @Autowired
    private EnvoyService envoyService;


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
            return prepareReturnResultDTO(ReturnCode.GET_SUCCESS,envoyService.findAllAdmin());
        }catch (Exception e){
            return prepareReturnResultDTO(ReturnCode.ERROR_QUERY,e.getMessage());
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
    @PostMapping("/unlockall")
    @ApiOperation(value = "解鎖所有", httpMethod = "POST", response = ResponseEntity.class, notes = "解鎖所有")
    public ReturnResultDTO<?> unlockall(@RequestParam(name = "userId") Long userId) {
        try {
//            UserInfo info = getToken();
            adminService.unLockAll(userId);
            return prepareReturnResultDTO(ReturnCode.UPDATE_SUCCESS,null);
        }catch (Exception e){
            return prepareReturnResultDTO(ReturnCode.ERROR_UPDATE,null);
        }
    }

    @PostMapping("/admin-card")
    @ApiOperation(value = "更新卡牌", httpMethod = "POST", response = ResponseEntity.class, notes = "更新卡牌")
    public ReturnResultDTO<?> adminChangeCard(@RequestBody AdminUpdateCardDTO adminUpdateCard) {
        try {
            adminService.adminChangeCard(adminUpdateCard);
            return prepareReturnResultDTO(ReturnCode.UPDATE_SUCCESS,null);
        }catch (Exception e){
            return prepareReturnResultDTO(ReturnCode.ERROR_UPDATE,e.getMessage());
        }
    }
    @PostMapping("/admin-envoy")
    @ApiOperation(value = "更新棋子", httpMethod = "POST", response = ResponseEntity.class, notes = "更新棋子")
    public ReturnResultDTO<?> adminChangeEnvoy(@RequestBody AdminUpdateEnvoyDTO adminUpdateEnvoyDTO) {
        try {
            adminService.adminChangeEnvoy(adminUpdateEnvoyDTO);
            return prepareReturnResultDTO(ReturnCode.UPDATE_SUCCESS,null);
        }catch (Exception e){
            return prepareReturnResultDTO(ReturnCode.ERROR_UPDATE,e.getMessage());
        }
    }
    //获取所有配置信息
    @GetMapping("/all-config")
    @ApiOperation(value = "获取所有配置信息", httpMethod = "POST", response = ResponseEntity.class, notes = "获取所有配置信息")
    public ReturnResultDTO<?> getAllConfig() {
        try {
            return prepareReturnResultDTO(ReturnCode.GET_SUCCESS,adminService.getAllConfig());
        }catch (Exception e){
            return prepareReturnResultDTO(ReturnCode.ERROR_QUERY,null);
        }
    }

}
