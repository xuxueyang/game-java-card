package acct.service;


import acct.domain.Account;
import acct.domain.Graph;
import acct.domain.Token;
import acct.domain.User;
import acct.repository.UserRepository;
import core.dto.acct.dto.UserInfo;
import acct.repository.GraphRepository;
import acct.repository.TokenRepository;
import core.protocol.Protocol;
import core.util.CaptchaGenerator;
import core.util.UUIDGenerator;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.servlet.http.HttpServletRequest;
import java.time.ZonedDateTime;
import java.util.*;

@Service
@Transactional
public class AcctLoginService {
//    private static ApplicationProperties APPLICATION_PROPERTIES = (ApplicationProperties) SpringLoginUtils.getBean(ApplicationProperties.class);

//
//    @Autowired
//    private UaaProperties uaaProperties;

    @Autowired
    private GraphRepository graphRepository;

    @Autowired
    private TokenRepository tokenRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenCacheService tokenCacheService;


    private final String AUTHORIZED_GRANT_TYPES_USERNAME = "username";
    private final String AUTHORIZED_GRANT_TYPES_PASSWORD = "password";
    private final String AUTHORIZED_GRANT_TYPES_REFRESH_TOKEN = "refresh_token";
    private final String AUTHORIZED_GRANT_TYPES = "grant_type";
    private final String GRAPH_TYPE_CODE = "code";
    private final String GRAPH_TYPE_NUM  = "num";


    public Long getAccountIdByToken(String header) {
        return new Long(1);// TODO
    }

    //生成graph
    public Map<String,Object> createGraph() throws Exception{
        //生成验证码和相应的编码图片
        //生成图形验证码
        String captchaCode = CaptchaGenerator.generateCaptchaCode(4);
        //Base64编码
        String captchaCode_base64 = CaptchaGenerator.outputImageBase64(300, 80, captchaCode);
        //保存
        Graph uaaGraph = new Graph();
        uaaGraph.setValue(captchaCode);
        uaaGraph.setType(GRAPH_TYPE_CODE);
        graphRepository.save(uaaGraph);

        Map<String ,Object> map = new HashMap<>();
        map.put("graph",captchaCode_base64);
        map.put("id",uaaGraph.getId());
        return map;
    }

    //验证graph
    public boolean verifyGraph(Long id,String value){
        if(id==null||value==null){
            return false;
        }
        Graph uaaGraph = graphRepository.findOne(id);
        if(uaaGraph==null){
            return false;
        }
        if(!uaaGraph.getValue().toUpperCase().equals(value.toUpperCase())){
            return false;
        }else{
            //删除
            graphRepository.delete(id);
            return true;
        }
    }
    public Map login(Account account,Long area) {
        //删除token
        List<Token> allByCreatedid = tokenRepository.findAllByCreatedBy(account.getId());
        if(allByCreatedid!=null||allByCreatedid.size()>0){
            for(Token token: allByCreatedid){
                tokenRepository.delete(token);
                tokenCacheService.deleteToken(token.getAccesstoken(),area);
            }
        }
        //登录，创建token
        Map map = new HashMap<String,Object>();
        //生成token
//        OAuth2AccessToken token = createToken(APPLICATION_PROPERTIES.getClient(),uaaUser.getId(), uaaUser.getTenantCode());
        String token = UUIDGenerator.getUUID();
        //技术原因，先只用token就好了
        map.put(Protocol.ACCESS_TOKEN,token);
//        map.put(Constants.REFRESH_TOKEN,token.getRefreshToken().getValue());

        //存到数据库里，token
        Token uaaToken = new Token();
        uaaToken.setAccesstoken(token);
        uaaToken.setCreatedBy(account.getId());
        uaaToken.setValidtime(Protocol.TOKEN_VALID_TIME);
        tokenRepository.save(uaaToken);
        //存userInfo
        map.put(Protocol.USERINFO,prepareForUserInfo(account,area));
        //TODO 存到緩存中
        tokenCacheService.saveLoginCache(account.getId(),area,token);

        return map;
    }


    public UserInfo prepareForUserInfo(Account account, Long area){
        //判斷有沒有創建用戶
        User user = userRepository.findOneByAccountIdAndArea(account.getId(), area);
        if(user==null){
            return null;
        }
        UserInfo userInfo = new UserInfo();
        userInfo.setEmail(account.getEmail());
        userInfo.setName(user.getNickName());
        userInfo.setAccountId(account.getId());
        userInfo.setId(user.getId());
        user.setArea(area);
        return userInfo;
    }
    public Token getUserByToken(String token){
        //如果token不存在或过期则返回null
        Token oneByAccesstoken = tokenRepository.findOneByAccesstoken(token);
        if(oneByAccesstoken==null)
            return null;
        ZonedDateTime toTime = oneByAccesstoken.getCreatedDate().plusSeconds(oneByAccesstoken.getValidtime());
        if(!toTime.isAfter(ZonedDateTime.now())){
            //删除token
            tokenRepository.delete(oneByAccesstoken.getId());
            return null;
        }
        return oneByAccesstoken;
    }

    public  String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader(" x-forwarded-for ");
        if (ip == null || ip.length() == 0 || " unknown ".equalsIgnoreCase(ip)) {
            ip = request.getHeader(" Proxy-Client-IP ");
        }
        if (ip == null || ip.length() == 0 || " unknown ".equalsIgnoreCase(ip)) {
            ip = request.getHeader(" WL-Proxy-Client-IP ");
        }
        if (ip == null || ip.length() == 0 || " unknown ".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }


}
