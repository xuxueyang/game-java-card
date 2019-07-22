package core.redis.dto;

/**
 * Created by wangg on 2017年6月8日.
 */
public class RedisValueDTO extends RedisKeyDTO {

    private String value;

    public RedisValueDTO() {
        super();
    }

    public RedisValueDTO(String tenantCode, String containerType, String containerId, String objectType, String objectId) {
        super(tenantCode, containerType, containerId, objectType, objectId);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
