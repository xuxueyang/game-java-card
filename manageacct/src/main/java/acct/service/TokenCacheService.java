package acct.service;

import core.dto.acct.dto.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.ConcurrentHashMap;

@Service
public class TokenCacheService {

    @Autowired
    private AcctService acctService;

    private static ConcurrentHashMap<String,String> accountIdPlusAreaToken = new ConcurrentHashMap<>();
    private static ConcurrentHashMap<String,String> tokenAccountIdPlusArea = new ConcurrentHashMap<>();

    private static ConcurrentHashMap<String,Long> tokenUserId = new ConcurrentHashMap<>();
    private static ConcurrentHashMap<Long,String> userIdToken = new ConcurrentHashMap<>();
//    private static ConcurrentHashMap<String,String> accountIdToken = new ConcurrentHashMap<>();
//    private static ConcurrentHashMap<String,String> accountIdToken = new ConcurrentHashMap<>();
    @Transactional
    public void saveLoginCache(Long accountId,Long area,String  token){
        accountIdPlusAreaToken.put(""+accountId+area,token);
        tokenAccountIdPlusArea.put(token,""+accountId+area);
    }
//    public boolean checkHasUser(){
//
//    }
    @Transactional
    public void deleteToken(String token) {
        if(tokenAccountIdPlusArea.contains(token)){
            String s = tokenAccountIdPlusArea.get(token);
            accountIdPlusAreaToken.remove(s);
            tokenAccountIdPlusArea.remove(token);
            if(tokenUserId.contains(token)){
                Long userId = tokenUserId.get(token);
                userIdToken.remove(userId);
                tokenUserId.remove(token);
            }
        }
    }

    public void createUser(Long accountId, Long area, Long userId) {
        //如果有token需要绑定token和userId
        if(accountIdPlusAreaToken.contains(""+accountId+area)){
            String token = accountIdPlusAreaToken.get("" + accountId + area);
            tokenUserId.put(token,userId);
            userIdToken.put(userId,token);
        }
    }
    public boolean checkLogin(String token){
        //返回userId
        if(tokenAccountIdPlusArea.contains(token))
            return true;
        return false;
    }
    public UserInfo getUserInfoByToken(String token){
        if(checkLogin(token)){
            if(tokenUserId.contains(token)){
                Long userId = tokenUserId.get(token);
                return acctService.getUserInfoByUserId(userId);
            }else{
                return null;
            }
        }
        return null;
    }
}
