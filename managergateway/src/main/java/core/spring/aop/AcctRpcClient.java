package core.spring.aop;


import core.core.RequestDTO;
import core.core.ReturnResultDTO;
import core.rpc.AcctRPCConstant;
import core.spring.aop.hystrix.AcctServiceHystrix;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.websocket.server.PathParam;
import java.util.Map;



@FeignClient(
        value = AcctRPCConstant.SERVICE_NAME//rpc服务所在的路径（通过spring cloud registry动态获取rpc服务的url地址）
//        ,url = CubeuaaUrl.SERVICE_URL //rpc服务所在的绝对路径，该参数会使'value'失效，
//        ,url = "${user-server-api.url}",
//        fallback = AcctServiceHystrix.class
        ,url = "http://localhost:20007"
)
@EnableFeignClients
public interface AcctRpcClient {
    @RequestMapping(method = RequestMethod.POST,value = AcctRPCConstant.GET_TOKEN_ID)
    ResponseEntity<ReturnResultDTO> getTokenById(@RequestBody  RequestDTO dto);

    @RequestMapping(method = RequestMethod.POST,value = AcctRPCConstant.CHECK_LOGIN)
    ResponseEntity<ReturnResultDTO> checkToken(@RequestBody  RequestDTO dto);

    @RequestMapping(method = RequestMethod.GET,value = AcctRPCConstant.TEST)
    ResponseEntity<ReturnResultDTO> test();
//        @RequestMapping(method = RequestMethod.POST, value = CubeuaaUrl.FIND_SUBSPACE_NAMES)
//        Map<String, String> findSubspaceNames(@RequestParam("filed") List<String> filed, @RequestParam(required = false, name = "searchFiledName") String searchFiled);

//    @RequestMapping(method = RequestMethod.GET, value = CubeuaaUrl.FIND_SUBSPACE_NAMES)
//    Map<String, String> findSubspaceNames(@RequestParam("filed") List<String> filed, @RequestParam(required = false, name = "searchFiledName") String searchFiled);
//
//    @RequestMapping(method = RequestMethod.GET, value = CubeuaaUrl.FIND_SUBSPACE_CODE_ALL_CHILDREN)
//    List<String> findSubspaceCodeAllChildren(@RequestParam("subspaceCode") String subspaceCode);
//
//    @RequestMapping(method = RequestMethod.GET, value = CubeuaaUrl.FIND_SUBSPACE_DETAIL)
//    SubspaceDetailRpcDto findSubspaceInfo(@RequestParam("spaceUri") String spaceUri);
//
//    @RequestMapping(method = RequestMethod.GET, value = CubeuaaUrl.USER_CACHE_USERS)
//    List<UserCacheInfo> findUsersByDb(@RequestParam("tenantCode") String tenantCode,
//                                      @RequestParam("spaceCode") String spaceCode,
//                                      @RequestParam("userIds") List<String> userIds);
//
//    @RequestMapping(method = RequestMethod.GET, value = CubeuaaUrl.USER_CACHE_CURRENT_USER)
//    UserInfo getCurrentUserInfo(@RequestBody CubeuaaRPCDto cubeuaaRPCDto);
//
//    @RequestMapping(method = RequestMethod.DELETE, value = CubeuaaUrl.USER_CACHE_DELETE_ACCTS)
//    boolean deleteAccts(@RequestParam("tenantCode") String tenantCode, @RequestParam("acctIds") String acctIds);
//
//    @RequestMapping(method = RequestMethod.GET, value = CubeuaaUrl.FIND_SPACE_DETAIL)
//    SpaceDetailRpcDto findSpaceInfo(@RequestParam("spaceUri") String spaceUri);
//
//    @RequestMapping(method = RequestMethod.GET, value = CubeuaaUrl.PERMISSION_ALL_NEED_AUTHENTICATE)
//    List<NeedAuthenticateRpcDto> getAllNeedAuthenticate();
//
//    @RequestMapping(method = RequestMethod.GET, value = CubeuaaUrl.PERMISSION_RESOURCE_PERMISSIONS)
//    List<String> getResourcePermissions(@RequestParam("resourceId") String resourceId, @RequestParam("tenantCode") String tenantCode,
//                                        @RequestParam("spaceCode") String spaceCode);
//
//    @RequestMapping(method = RequestMethod.GET, value = CubeuaaUrl.PERMISSION_ALL_ACCESS_CONTROL_RESOURCES)
//    List<AccessControlRpcDto> getAllAccessControlResourcesByRequestAndUrl(@RequestParam("request") String request, @RequestParam("url") String url,
//                                                                          @RequestParam("instanceCode") String instanceCode, @RequestParam("tenantCode") String tenantCode,
//                                                                          @RequestParam("spaceCode") String spaceCode);
//
//    @RequestMapping(method = RequestMethod.GET, value = CubeuaaUrl.PERMISSION_CHECK_USER_PERMISSIONS)
//    boolean checkUserPermissions(@RequestParam("permissions") List<String> permissions, @RequestParam("userId") String userId,
//                                 @RequestParam("tenantCode") String tenantCode, @RequestParam("spaceCode") String spaceCode);
//
//    @RequestMapping(method = RequestMethod.GET, value = CubeuaaUrl.PERMISSION_USER_ROLES)
//    List<RoleInfo> getUserRoles(@RequestParam("userId") String userId,
//                                @RequestParam("tenantCode") String tenantCode, @RequestParam("spaceCode") String spaceCode);
}

