package itemmanager.web.rest;

import core.core.BaseResource;
import core.core.ReturnCode;
import core.core.ReturnResultDTO;
import core.dto.acct.dto.UserInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import itemmanager.aop.NeedLoginAspect;
import itemmanager.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 卡包管理，可以開啓卡包，隨機獲取卡牌。棋子只能通過購買！
 */
@Api(value = "管理權限", description = "管理權限")
@RestController("/api/admin")
@NeedLoginAspect
public class AdminResource extends ItemBaseResource {
    //解鎖某個用戶所有卡牌和棋子
    @Autowired
    private AdminService adminService;

    @PostMapping("/unlockall")
    @ApiOperation(value = "解鎖所有", httpMethod = "POST", response = ResponseEntity.class, notes = "解鎖所有")
    public ReturnResultDTO<?> unlockall() {
        try {
            UserInfo info = getToken();
            adminService.unLockAll(info.getId());
            return prepareReturnResultDTO(ReturnCode.UPDATE_SUCCESS,null);
        }catch (Exception e){
            return prepareReturnResultDTO(ReturnCode.ERROR_UPDATE,null);
        }
    }
}
