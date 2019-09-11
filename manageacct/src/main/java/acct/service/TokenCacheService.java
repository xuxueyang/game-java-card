package acct.service;

import acct.domain.Account;
import acct.domain.Token;
import acct.domain.User;
import acct.repository.AccountRepository;
import acct.repository.TokenRepository;
import acct.repository.UserRepository;
import core.dto.acct.dto.UserInfo;
import core.protocol.Protocol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TokenCacheService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TokenRepository tokenRepository;
    @PostConstruct
    public void refreshCache(){
        List<Account> all = accountRepository.findAll();
        if(all!=null&&all.size()>0){
            for (Account account : all) {
                Token token = tokenRepository.findOneByCreatedBy(account.getId());
                if(token!=null){
                    Long[] allArea = Protocol.AreaL.getAllArea();
                    if(allArea!=null){
                        for (Long aLong : allArea) {
                            saveLoginCache(account.getId(),aLong,token.getAccesstoken());
                        }
                    }
                }
            }
        }
    }
    @Autowired
    private AcctService acctService;

    private static ConcurrentHashMap<String,String> accountIdPlusAreaToken = new ConcurrentHashMap<>();
    private static ConcurrentHashMap<String,String> tokenAccountIdPlusArea = new ConcurrentHashMap<>();

    private static ConcurrentHashMap<String,String> tokenUserIdPlusArea = new ConcurrentHashMap<>();
    private static ConcurrentHashMap<String,String> userIdPlusAreaToken = new ConcurrentHashMap<>();
//    private static ConcurrentHashMap<String,String> accountIdToken = new ConcurrentHashMap<>();
//    private static ConcurrentHashMap<String,String> accountIdToken = new ConcurrentHashMap<>();
    @Transactional
    public void saveLoginCache(Long accountId,Long area,String  token){
        accountIdPlusAreaToken.put(""+accountId+Protocol.AreaL.split+area,token);
        tokenAccountIdPlusArea.put(token,""+accountId+Protocol.AreaL.split+area);
        //如果有用户的话
        User user = userRepository.findOneByAccountIdAndArea(accountId, area);
        if(user!=null){
            tokenUserIdPlusArea.put(token,user.getId() + Protocol.AreaL.split + area);
            userIdPlusAreaToken.put(user.getId() + Protocol.AreaL.split + area,token);
        }

    }
//    public boolean checkHasUser(){
//
//    }
    @Transactional
    public void deleteToken(String token,Long area) {
        if(tokenAccountIdPlusArea.containsKey(token)){
            String s = tokenAccountIdPlusArea.get(token);
            accountIdPlusAreaToken.remove(s);
            tokenAccountIdPlusArea.remove(token);
            if(tokenUserIdPlusArea.containsKey(token)){
                String key = tokenUserIdPlusArea.get(token);
                userIdPlusAreaToken.remove(key);
                tokenUserIdPlusArea.remove(token);
            }
        }
    }

    public void createUser(Long accountId, Long area, Long userId) {
        //如果有token需要绑定token和userId
        if(accountIdPlusAreaToken.containsKey(""+accountId+Protocol.AreaL.split+area)){
            String token = accountIdPlusAreaToken.get("" + accountId +Protocol.AreaL.split+ area);
            tokenUserIdPlusArea.put(token,userId+Protocol.AreaL.split+ area);
            userIdPlusAreaToken.put(userId+Protocol.AreaL.split+ area,token);
        }
    }
    public boolean checkLogin(String token){
        //返回userId
        if(tokenAccountIdPlusArea.containsKey(token))
            return true;
        return false;
    }
    public UserInfo getUserInfoByToken(String token){
        if(checkLogin(token)){
            if(tokenUserIdPlusArea.containsKey(token)){
                String key = tokenUserIdPlusArea.get(token);
                return acctService.getUserInfoByUserId(Long.parseLong(key.split(Protocol.AreaL.split)[0]));
            }else{
                return null;
            }
        }
        return null;
    }

    public String getToeknById(String id,Long area) {
        if(id!=null&&userIdPlusAreaToken.containsKey(id+Protocol.AreaL.split+ area)){
            return userIdPlusAreaToken.get(id+Protocol.AreaL.split+ area);
        }else{
            return null;
        }
    }
}
