package itemmanager.web.rest;

import core.core.BaseResource;
import core.core.RequestDTO;
import core.core.ReturnResultDTO;
import core.dto.acct.dto.UserInfo;
import core.protocol.Protocol;
import itemmanager.aop.AcctRpcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class ItemBaseResource extends BaseResource {
    @Autowired
    private AcctRpcClient acctRpcClient;

    protected UserInfo getToken(){
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
