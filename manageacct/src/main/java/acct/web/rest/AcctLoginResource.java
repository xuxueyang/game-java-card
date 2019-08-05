package acct.web.rest;

import acct.domain.Account;
import core.dto.acct.dto.LoginDTO;
import acct.service.AcctLoginService;
import acct.service.AcctService;
import core.core.BaseResource;
import core.core.ReturnCode;
import core.core.ReturnResultDTO;
import core.util.Validators;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@Api(value = "登录系统",description = "登录系统")
@RestController
@RequestMapping("/api")
public class AcctLoginResource extends BaseResource{

    static {
        // 防止二维码显示不了
        System.setProperty("java.awt.headless","true");
    }

    @Autowired
    private AcctService acctService;
    @Autowired
    private AcctLoginService acctLoginService;


    @GetMapping("/login/graph")
    @ApiOperation(value = "获取验证码", httpMethod = "GET", response = ResponseEntity.class, notes = "获取验证码")
    public ReturnResultDTO<?> getGraph() {
        try {
            Map<String,Object> map = acctLoginService.createGraph();
            return prepareReturnResultDTO(ReturnCode.GET_SUCCESS,map);
        }catch (Exception e){
            return prepareReturnResultDTO(ReturnCode.ERROR_GRAPH_CODE,null);
        }
    }


    @PostMapping("/login/authenticate")
    @ApiOperation(value = "普通登录", notes = "必填：用户名、密码； 选填：graphCaptchaCodeId,graphCaptchaCode")
    public ReturnResultDTO login(final @RequestBody LoginDTO loginDTO){
        try {
            //判断字段
            if(Validators.fieldBlank(loginDTO.getName())||
                    Validators.fieldBlank(loginDTO.getPassword())){
                return prepareReturnResultDTO(ReturnCode.ERROR_FIELD_EMPTY,null);
            }
            //验证验证码
            //前期先判断如果ID不为空再验证
            if(!Validators.fieldBlank(loginDTO.getGraphCaptchaCodeId())){
                if(!acctLoginService.verifyGraph(loginDTO.getGraphCaptchaCodeId(),loginDTO.getGraphCaptchaCode())){
                    return prepareReturnResultDTO(ReturnCode.ERROR_GRAPH_CODE,null);
                }
            }
            //判断有没有空间，且看见下有没有这个用户
            Account account = acctService.findUserByLoginName(loginDTO.getName());
            if(account==null){
                return prepareReturnResultDTO(ReturnCode.ERROR_PASSWORD_NOT_CORRECT_CODE,null);
            }
            //判断有没有空间，且看见下有没有这个用户
//            {
//                if(Validators.fieldNotBlank(loginDTO.getTenantCode())){
//                    UaaTenantCode tenant = getTenant(loginDTO.getTenantCode());
//                    if(tenant==null){
//                        return prepareReturnResult(ReturnCode.ERROR_HEADER_NOT_TENANT_CODE,null);
//                    }
//                }
//            }
            if(!account.getPassword().equals(loginDTO.getPassword())){
                return prepareReturnResultDTO(ReturnCode.ERROR_PASSWORD_NOT_CORRECT_CODE,null);
            }
            Map<String,Object> map = acctLoginService.login(account);
            return prepareReturnResultDTO(ReturnCode.CREATE_SUCCESS,map);
        }catch (Exception e){
            return prepareReturnResultDTO(ReturnCode.ERROR_LOGIN,null);
        }
    }


}
