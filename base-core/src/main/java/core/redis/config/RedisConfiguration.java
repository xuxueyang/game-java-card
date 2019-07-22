package core.redis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.*;

/**
 * Created by wangg on 2017年6月8日.
 */
//@Configuration
public class RedisConfiguration {

	/**
	 * 实例化 HashOperations 对象,可以使用 Hash 类型操作
	 * 
	 * @param redisTemplate
	 * @return
	 */
	@Bean
	public HashOperations<String, String, String> hashOperations(StringRedisTemplate redisTemplate) {
		return redisTemplate.opsForHash();
	}

	/**
	 * 实例化 ValueOperations 对象,可以使用 String 操作
	 * 
	 * @param redisTemplate
	 * @return
	 */
	@Bean
	public ValueOperations<String, String> valueOperations(StringRedisTemplate redisTemplate) {
		return redisTemplate.opsForValue();
	}

	/**
	 * 实例化 ListOperations 对象,可以使用 List 操作
	 * 
	 * @param redisTemplate
	 * @return
	 */
	@Bean
	public ListOperations<String, String> listOperations(StringRedisTemplate redisTemplate) {
		return redisTemplate.opsForList();
	}

	/**
	 * 实例化 SetOperations 对象,可以使用 Set 操作
	 * 
	 * @param redisTemplate
	 * @return
	 */
	@Bean
	public SetOperations<String, String> setOperations(StringRedisTemplate redisTemplate) {
		return redisTemplate.opsForSet();
	}

}
