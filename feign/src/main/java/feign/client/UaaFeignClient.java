package feign.client;

//import com.fengyun.udf.cache.dto.EnterpriseCacheDto;
//import com.fengyun.udf.cache.dto.PersonalCacheDto;
//import com.fengyun.udf.cache.dto.UserInfoDTO;
//import com.fengyun.udf.dto.ReturnResultDTO;
//import com.fengyun.udf.feign.dto.*;
import feign.constant.FeignConstants;
import org.springframework.cloud.openfeign.FeignClient;

import java.util.List;

/**
 * Created by wangg on 2019/7/12.
 */
@FeignClient(value = FeignConstants.SERVICE_UAA)
public interface UaaFeignClient {

//    @PostMapping("/api/log")
//    ReturnResultDTO saveLog(@RequestBody FeignAuditLogDTO feignAuditLog);
//
//    @GetMapping("/rpc/enterprise/infos")
//    List<EnterpriseCacheDto> findEnterprise();
//
//    @GetMapping("/rpc/enterprise/info")
//    EnterpriseCacheDto findEnterprise(@RequestParam(name = "userId") String userId);
//
//    @GetMapping("/rpc/user/info")
//    UserInfoDTO findUserCache(@RequestParam(name = "id") String id);
//
//    @GetMapping("/rpc/personal/info")
//    PersonalCacheDto findDetailByUserId(@RequestParam(name = "userId") String userId);
//
//    /**
//     * 消息中心-保存发送的消息
//     * @param dto
//     */
//    @PostMapping("/rpc/message/save")
//    void createMessage(@RequestBody CreateMessageCenterDTO dto);
//
//    /**
//     * 我的订阅-获取用户该资源的订阅情况
//     * @param userId
//     * @param resourceType
//     * @param resourceId
//     * @return
//     */
//    @GetMapping("/rpc/subscribe")
//    UserSubscribeDTO getUserSubscribe(@RequestParam(name = "userId") String userId
//            , @RequestParam(name = "resourceType") String resourceType, @RequestParam(name = "resourceId") String resourceId);
//
//    /**
//     * 我的订阅-资源删除或状态变为不可查看时，删除所有相关订阅
//     * @param resourceType
//     * @param resourceId
//     */
//    @DeleteMapping("/rpc/subscribe")
//    void deleteSubscribe(@RequestParam(name = "resourceType") String resourceType, @RequestParam(name = "resourceId") String resourceId);
//
//    /**
//     * 我的订阅-更新资源时同步更新订阅资源的名称
//     * @param dto
//     */
//    @PutMapping("/rpc/subscribe")
//    void updateUserSubscribe(@RequestBody UpdateUserSubscribeDTO dto);
//
//    /**
//     * 我的关注-获取用户该对象的关注情况
//     * @param userId
//     * @param resourceId
//     * @return
//     */
//    @GetMapping("/rpc/follow")
//    UserFollowDTO getUserFollow(@RequestParam(name = "userId") String userId, @RequestParam(name = "resourceId") String resourceId);
//
//    /**
//     * 我的关注-对象删除或状态变为不可查看时，删除所有相关关注
//     * @param resourceId
//     */
//    @DeleteMapping("/rpc/follow")
//    void deleteUserFollow(@RequestParam(name = "resourceId") String resourceId);
//
//    /**
//     * 我的关注-更新资源时同步更新关注资源的名称
//     * @param dto
//     */
//    @PutMapping("/rpc/follow")
//    void updateUserFollow(@RequestBody UpdateUserFollowDTO dto);
//
//    /**
//     * 待办事项-新增待办
//     * @param dto
//     */
//    @PostMapping("/rpc/todo/save")
//    void createUserTodo(@RequestBody CreateUserTodoDTO dto);
//
//    /**
//     * 添加用户积分
//     * @param actionCode
//     * @param userId
//     * @param tenantCode
//     */
//    @PostMapping("/rpc/user/point")
//    void addUserPoints(@RequestParam(name = "actionCode") String actionCode
//            , @RequestParam(name = "userId") String userId, @RequestParam(name = "tenantCode") String tenantCode);
}
