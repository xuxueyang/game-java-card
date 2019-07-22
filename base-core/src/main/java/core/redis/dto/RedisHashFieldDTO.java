package core.redis.dto;

/**
 * Created by wangg on 2017年6月8日.
 */
public class RedisHashFieldDTO extends RedisKeyDTO {

    private String hashKey;
    private String fieldValue;

    public RedisHashFieldDTO() {
        super();
    }

    public RedisHashFieldDTO(String tenantCode, String containerType, String containerId, String objectType, String objectId) {
        super(tenantCode, containerType, containerId, objectType, objectId);
    }

    public String getHashKey() {
        return hashKey;
    }

    public void setHashKey(String hashKey) {
        this.hashKey = hashKey;
    }


    public String getFieldValue() {
        return fieldValue;
    }


    public void setFieldValue(String fieldValue) {
        this.fieldValue = fieldValue;
    }

}
