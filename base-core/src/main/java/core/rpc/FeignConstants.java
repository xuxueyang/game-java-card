package core.rpc;

//import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface FeignConstants {
    String SERVICE_ROOM = "room";


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
//    @PostMapping("/rpc/message/save/batch")
//    void batchCreateMessage(@RequestBody List<CreateMessageCenterDTO> ls);
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
//     * 我的订阅-订阅类型下用户订阅列表
//     * @param userId
//     * @param resourceType
//     */
//    @GetMapping("/rpc/type/subscribe")
//    List<String> getResourceIdListSubscribe(@RequestParam(name = "userId")String userId,@RequestParam(name = "resourceType") String resourceType);
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
//
//    /**
//     * 待办事项-删除待办
//     * @param todoId
//     * @param todoType
//     * @param userId
//     */
//    @DeleteMapping("/rpc/todo/delete")
//    void deleteUserTodo(@RequestParam(name = "todoId") String todoId
//            , @RequestParam(name = "todoType") String todoType, @RequestParam(name = "userId") String userId);
//
//
//    /**
//     * 成为人才
//     * @param userId
//     */
//    @PostMapping("/rpc/personal/talent")
//    public void updateTalent(@RequestParam(name = "userId")String userId);
//
//    /**
//     * 成为专家
//     * @param userId
//     */
//    @PostMapping("/rpc/personal/expert")
//    public void updateExpert(@RequestParam(name = "userId") String userId);
//
//
//    /**
//     * 新增热搜词汇
//     * @param type
//     * @param words
//     */
//    @PostMapping("/rpc/hot-word")
//    public void addHotWords(@RequestParam(name = "type") String type,@RequestParam(name = "words") String words);
//
//
//    @PostMapping("/rpc/sms")
//    public void sendSmsMessage(@RequestBody List<SmsMessageDto> msgs);
//
//    @PostMapping("/rpc/points")
//    public void addUserPoints(@RequestParam(name = "userId") String userId, @RequestParam(name = "code") String code);
//
//    @PostMapping("/rpc/user/follow")
//    public boolean getUserExistFollow(@RequestParam(name = "userId") String userId, @RequestParam(name = "resourceId") String resourceId);
//
//
//    /**
//     * 替换敏感词
//     * @param content
//     * @return
//     */
//    @GetMapping("/rpc/sensitive-word")
//    public String replaceSensitiveWord(@RequestParam(name = "content") String content);
//
//
//    @PostMapping("/rpc/file/upload")
//    public String uploadFile(@RequestBody FileUploadDto fileUploadDto);
//
//    @GetMapping("/rpc/enterprise/growth/trend")
//    List<EnterpriseGrowthTrendDto> enterpriseGrowthTrend();
//
//
//    @GetMapping("/rpc/enterprise/industry")
//    List<EnterpriseIndustryTypeDto> enterpriseIndustryType();
//
//    /**
//     * 资源统计
//     * @return
//     */
//    @GetMapping("/rpc/uaa/resource")
//    public List<StatisticsDto> resourceCount();
//
//
//    @GetMapping("/rpc/dict/value")
//    public List<String> getValues(@RequestParam(name = "code") String code);
//
//    /**
//     * 按年查询企业数量
//     * @return
//     */
//    @GetMapping("/rpc/enterprise/growth/count")
//    public Integer enterpriseCount(@RequestParam(name = "date") String date);
//
//    /** Select group detail info by group id */
//    @GetMapping("/rpc/group")
//    public GroupDTO findGroupById(@RequestParam(name = "id") String id);
//
//    @GetMapping("/rpc/user/groupIds")
//    public String findUserGroupIds(@RequestParam(name = "userId") String userId);

}
