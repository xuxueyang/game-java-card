package acct.service;


import acct.domain.Account;
import acct.domain.Graph;
import acct.domain.Token;
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




    private final String AUTHORIZED_GRANT_TYPES_USERNAME = "username";
    private final String AUTHORIZED_GRANT_TYPES_PASSWORD = "password";
    private final String AUTHORIZED_GRANT_TYPES_REFRESH_TOKEN = "refresh_token";
    private final String AUTHORIZED_GRANT_TYPES = "grant_type";
    private final String GRAPH_TYPE_CODE = "code";
    private final String GRAPH_TYPE_NUM  = "num";


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
    public Map login(Account account) {
        //删除token
        List<Token> allByCreatedid = tokenRepository.findAllByCreatedBy(account.getId());
        if(allByCreatedid!=null||allByCreatedid.size()>0){
            for(Token token: allByCreatedid){
                tokenRepository.delete(token);
            }
        }
        //登录，创建token
        Map map = new HashMap<String,Object>();
        //生成token
//        OAuth2AccessToken token = createToken(APPLICATION_PROPERTIES.getClient(),uaaUser.getId(), uaaUser.getTenantCode());
        String token = createToken(account.getId());
        //技术原因，先只用token就好了
        map.put(Protocol.ACCESS_TOKEN,token);
//        map.put(Constants.REFRESH_TOKEN,token.getRefreshToken().getValue());
        //存userInfo
        map.put(Protocol.USERINFO,prepareForUserInfo(account));
        //存到数据库里，token
        Token uaaToken = new Token();
        uaaToken.setAccesstoken(token);
//        uaaToken.setRefreshtoken(token.getRefreshToken().getValue());
        uaaToken.setCreatedBy(account.getId());
        uaaToken.setValidtime(Protocol.TOKEN_VALID_TIME);
        tokenRepository.save(uaaToken);

        return map;
    }

    private String createToken( Long id) {
        return UUIDGenerator.getUUID();
    }

    public UserInfo prepareForUserInfo(Account account){
//        if(uaaUser==null)
        if(Protocol.Status.YES == account.getIsDeleted())
            return null;
        UserInfo userInfo = new UserInfo();
        userInfo.setEmail(account.getEmail());
        userInfo.setName(account.getLoginName());
        userInfo.setId(account.getId());
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
//    public OAuth2AccessToken getToken(String userId){
//        String clientId = uaaProperties.getWebClientConfiguration().getClientId();
//        Map<String, String> parameters = new HashMap<>();
//        parameters.put(AUTHORIZED_GRANT_TYPES, AUTHORIZED_GRANT_TYPES_PASSWORD);
//        parameters.put(AUTHORIZED_GRANT_TYPES_USERNAME, userId);
//
//        ClientDetails authenticatedClient = clientDetailsService.loadClientByClientId(clientId);
//        TokenRequest tokenRequest = oAuth2RequestFactory.createTokenRequest(parameters, authenticatedClient);
//
//        if (clientId != null && !clientId.equals("")) {
//            if (!clientId.equals(tokenRequest.getClientId())) {
//                throw new InvalidClientException("Given client ID does not match authenticated client");
//            }
//        }
//
//        if (authenticatedClient != null) {
//            OAuth2RequestValidator oAuth2RequestValidator = new DefaultOAuth2RequestValidator();
//            oAuth2RequestValidator.validateScope(tokenRequest, authenticatedClient);
//        }
//        if (!StringUtils.hasText(tokenRequest.getGrantType())) {
//            throw new InvalidRequestException("Missing grant type");
//        }
//        if (tokenRequest.getGrantType().equals("implicit")) {
//            throw new InvalidGrantException("Implicit grant type not supported from token endpoint");
//        }
//
//        if (isAuthCodeRequest(parameters)) {
//            // The scope was requested or determined during the authorization step
//            if (!tokenRequest.getScope().isEmpty()) {
//                tokenRequest.setScope(Collections.<String>emptySet());
//            }
//        }
//
//        if (isRefreshTokenRequest(parameters)) {
//            // A refresh token has its own default scopes, so we should ignore any added by the factory here.
//            tokenRequest.setScope(OAuth2Utils.parseParameterList(parameters.get(OAuth2Utils.SCOPE)));
//        }
//
//        //OAuth2AccessToken token = tokenGranter.grant(tokenRequest.getGrantType(), tokenRequest);
//        OAuth2Request storedOAuth2Request = oAuth2RequestFactory.createOAuth2Request(authenticatedClient, tokenRequest);
//
//        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
//
//        grantedAuthorities.add((new SimpleGrantedAuthority("anonymoususer")));
//
//        Authentication userAuth = new UsernamePasswordAuthenticationToken(userId, "", grantedAuthorities);
//        OAuth2AccessToken token = tokenServices.createAccessToken(new OAuth2Authentication(storedOAuth2Request, userAuth));
//        if (token == null) {
//            throw new UnsupportedGrantTypeException("Unsupported grant type: " + tokenRequest.getGrantType());
//        }
//
//        return token;
//    }
//    private boolean isAuthCodeRequest(Map<String, String> parameters) {
//        return "authorization_code".equals(parameters.get("grant_type")) && parameters.get("code") != null;
//    }
//
//    private boolean isRefreshTokenRequest(Map<String, String> parameters) {
//        return "refresh_token".equals(parameters.get("grant_type")) && parameters.get("refresh_token") != null;
//    }


}
