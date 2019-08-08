package core.spring.web.rest;


import core.core.BaseResource;
import core.core.RequestDTO;
import core.core.ReturnCode;
import core.core.ReturnResultDTO;
import core.dto.acct.dto.UserInfo;
import core.protocol.Protocol;
import core.spring.aop.AcctRpcClient;
import core.spring.service.match.MatchService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@RestController
@RequestMapping("/api/match")
public class MatchRoomResource extends BaseResource{
    //todo 加入戰鬥匹配，退出戰鬥匹配，通知進入戰鬥房間了

    @Autowired
    private AcctRpcClient acctRpcClient;
    @Autowired
    private MatchService matchService;

    @PostMapping("/join-two-room")
    @ApiOperation(value = "加入", httpMethod = "POST", response = ResponseEntity.class, notes = "加入")
    public ReturnResultDTO joinTwoRoom(){
        try {
            UserInfo userInfo = getUserInfo();
            boolean b = matchService.joinTwoRoom(userInfo);
            if(b)
                return prepareReturnResultDTO(ReturnCode.UPDATE_SUCCESS,null);
            else
                return prepareReturnResultDTO(ReturnCode.ERROR_CREATE,"匹配異常，請稍後再試");
        }catch (Exception e){
            return prepareReturnResultDTO(ReturnCode.ERROR_CREATE,e.getMessage());
        }
    }
    @DeleteMapping("/join-two-room")
    @ApiOperation(value = "退出", httpMethod = "DELETE", response = ResponseEntity.class, notes = "退出")
    public ReturnResultDTO quitTwoRoom(){
        try {
            UserInfo userInfo = getUserInfo();
            matchService.quitTwoRoom(userInfo);
            return prepareReturnResultDTO(ReturnCode.UPDATE_SUCCESS,null);
        }catch (Exception e){
            return prepareReturnResultDTO(ReturnCode.ERROR_UPDATE,e.getMessage());
        }
    }

    protected String getToken(){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return attributes.getRequest().getHeader(Protocol.TOKEN);
    }
    protected UserInfo getUserInfo(){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        String header = attributes.getRequest().getHeader(Protocol.TOKEN);
        RequestDTO dto = new RequestDTO();
        dto.setData(header);
        ReturnResultDTO userInfoByToken = acctRpcClient.getUserInfoByToken(dto);
        if(userInfoByToken!=null){
            if(userInfoByToken.getReturnCode().startsWith("200")&&userInfoByToken.getData() instanceof UserInfo){
                return (UserInfo)userInfoByToken.getData();
            }else{

            }
        }

        return null;
    }
}
