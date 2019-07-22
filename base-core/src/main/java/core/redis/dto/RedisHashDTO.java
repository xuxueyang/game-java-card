package core.redis.dto;

import java.util.Map;

/**
 * Created by wangg on 2017年6月8日.
 */
public class RedisHashDTO extends RedisKeyDTO {

    private Map<String, String> value;
    
    private Long timeout;

    public Map<String, String> getValue() {
        return value;
    }

    public void setValue(Map<String, String> value) {
        this.value = value;
    }

    public RedisHashDTO() {
        super();
    }

    public RedisHashDTO(String tenantCode, String containerType, String containerId, String objectType, String objectId) {
        super(tenantCode, containerType, containerId, objectType, objectId);
    }

	public Long getTimeout() {
		return timeout;
	}

	public void setTimeout(Long timeout) {
		this.timeout = timeout;
	}

}
