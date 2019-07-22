package core.redis.dto;

import java.util.List;

/**
* Created by wangg on 2017年6月8日.
*/
public class RedisListDTO extends RedisKeyDTO{
	
	private List<String> value;
	
	private Long timeout;
	
	public RedisListDTO() {
		super();
	}

	public RedisListDTO(String tenantCode, String containerType, String containerId, String objectType,
			String objectId) {
		super(tenantCode, containerType, containerId, objectType, objectId);
	}

	public List<String> getValue() {
		return value;
	}

	public void setValue(List<String> value) {
		this.value = value;
	}

	public Long getTimeout() {
		return timeout;
	}

	public void setTimeout(Long timeout) {
		this.timeout = timeout;
	}

}
