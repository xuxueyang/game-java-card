package core.redis.dto;

/**
* Created by wangg on 2017年6月8日.
*/
public class RedisExpireDTO extends RedisKeyDTO{
	
	private Long timeout;
	
	public Long getTimeout() {
		return timeout;
	}
	public void setTimeout(Long timeout) {
		this.timeout = timeout;
	}
}
