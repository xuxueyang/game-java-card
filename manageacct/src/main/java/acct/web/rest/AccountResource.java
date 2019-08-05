package acct.web.rest;


import acct.dto.CreateAccountDTO;
import acct.dto.CreateUserDTO;
import acct.service.AcctLoginService;
import acct.service.AcctService;
import com.netflix.discovery.converters.Auto;
import core.core.BaseResource;
import core.core.ReturnCode;
import core.core.ReturnResultDTO;
import core.protocol.Protocol;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Api(value = "賬號管理",description = "賬號管理")
@RestController
@RequestMapping("/api")
public class AccountResource  extends BaseResource {
    @Autowired
    private AcctService acctService;

    @Autowired
    private AcctLoginService loginService;

    @PostMapping("/account/create")
    public ReturnResultDTO createAccount(@RequestBody CreateAccountDTO createAccountDTO){
        try {
            acctService.createAccount(createAccountDTO);
            return prepareReturnResultDTO(ReturnCode.CREATE_SUCCESS,null);
        }catch (Exception e){
            return prepareReturnResultDTO(ReturnCode.ERROR_CREATE,e.getMessage());
        }
    }
    @PostMapping("/account/user")
    public ReturnResultDTO createUser(@RequestBody CreateUserDTO createUserDTO){
        try {
            //需要登录校验
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            javax.servlet.http.HttpServletRequest request = attributes.getRequest();
            String header = attributes.getRequest().getHeader(Protocol.TOKEN);
            Long accountId = loginService.getAccountIdByToken(header);
            //TODO 校驗area不能用這個賬號
            acctService.createUser(createUserDTO,accountId);
            return prepareReturnResultDTO(ReturnCode.CREATE_SUCCESS,null);
        }catch (Exception e){
            return prepareReturnResultDTO(ReturnCode.ERROR_CREATE,e.getMessage());
        }
    }
}
