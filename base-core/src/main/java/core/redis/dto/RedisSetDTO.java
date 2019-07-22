package core.redis.dto;

import java.util.Set;

/**
* Created by wangg on 2017年6月8日.
*/
public class RedisSetDTO extends RedisKeyDTO{
	
	private Set<String> value;
	
    private Long timeout;
	
	public RedisSetDTO() {
		super();
	}

	public RedisSetDTO(String tenantCode, String containerType, String containerId, String objectType,
			String objectId) {
		super(tenantCode, containerType, containerId, objectType, objectId);
	}

	public Set<String> getValue() {
		return value;
	}

	public void setValue(Set<String> value) {
		this.value = value;
	}

	public Long getTimeout() {
		return timeout;
	}

	public void setTimeout(Long timeout) {
		this.timeout = timeout;
	}

}
