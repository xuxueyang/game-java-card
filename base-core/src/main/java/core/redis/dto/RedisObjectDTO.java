package core.redis.dto;

/**
 * Created by wangg on 2017年6月8日.
 */
public class RedisObjectDTO extends RedisKeyDTO {

    private Object value;
    /**
     * 缓存过期时间，单位：秒；没有过期时间则不设值
     */
    private Long timeout;


    public RedisObjectDTO() {
        super();
    }

    public RedisObjectDTO(String tenantCode, String containerType, String containerId, String objectType, String objectId) {
        super(tenantCode, containerType, containerId, objectType, objectId);
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Long getTimeout() {
        return timeout;
    }

    public void setTimeout(Long timeout) {
        this.timeout = timeout;
    }

}
