package core.redis.dto;


import core.redis.SpringRedisUtils;
import core.redis.config.CubeProperties;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by wangg on 2017年6月8日.
 */
public class RedisKeyDTO {

    private static CubeProperties CUBE_PROPERTIES = SpringRedisUtils.getBean(CubeProperties.class);
    public static final String PROJECT_CODE = CUBE_PROPERTIES.getProjectCode();

    private String tenantCode;
    private String containerType;
    private String containerId;
    private String objectType;
    private String objectId;

    public RedisKeyDTO() {
    }

    public RedisKeyDTO(String tenantCode, String containerType, String containerId, String objectType, String objectId) {
        super();
        this.tenantCode = tenantCode;
        this.containerType = containerType;
        this.containerId = containerId;
        this.objectType = objectType;
        this.objectId = objectId;
    }

    public String getTenantCode() {
        return tenantCode;
    }

    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode;
    }

    public String getContainerType() {
        return containerType;
    }

    public void setContainerType(String containerType) {
        this.containerType = containerType;
    }

    public String getContainerId() {
        return containerId;
    }

    public void setContainerId(String containerId) {
        this.containerId = containerId;
    }

    public String getObjectType() {
        return objectType;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }
    
    public String getKey(){
		StringBuilder key = new StringBuilder();
        if(StringUtils.isNotBlank(PROJECT_CODE)){
            key.append(":");
            key.append(PROJECT_CODE);
        }
		if(StringUtils.isNotBlank(tenantCode)){
			key.append(":");
			key.append(tenantCode);
		}
		if(StringUtils.isNotBlank(containerType)){
			key.append(":");
			key.append(containerType);
		}
		if(StringUtils.isNotBlank(containerId)){
			key.append(":");
			key.append(containerId);
		}
		if(StringUtils.isNotBlank(objectType)){
			key.append(":");
			key.append(objectType);
		}
		if(StringUtils.isNotBlank(objectId)){
			key.append(":");
			key.append(objectId);
		}
		if(key != null && key.length() > 0){
			return key.deleteCharAt(0).toString();
		}else{
			return null;
		}
	}

}
