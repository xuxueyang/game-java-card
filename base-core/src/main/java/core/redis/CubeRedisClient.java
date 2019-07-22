package core.redis;

import com.alibaba.fastjson.JSON;
import core.redis.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

//@Component
public class CubeRedisClient {
    @Autowired
    private HashOperations<String, String, String> hashOperations;

    @Autowired
    private ValueOperations<String, String> valueOperations;

    @Autowired
    private ListOperations<String, String> listOperations;

    @Autowired
    private SetOperations<String, String> setOperations;

    @Autowired
    private StringRedisTemplate redisTemplate;

    public void setObject(RedisObjectDTO redisObjectDTO) {
        String key = redisObjectDTO.getKey();
        Object value = redisObjectDTO.getValue();
        String strValue = null;
        if (value instanceof String) {
            strValue = (String) value;
        } else {
            strValue = JSON.toJSONString(value);
        }
        if (redisObjectDTO.getTimeout() != null) {
            valueOperations.set(key, strValue, redisObjectDTO.getTimeout(), TimeUnit.SECONDS);
        } else {
            valueOperations.set(key, strValue);
        }
    }

    public String getObject(RedisObjectDTO redisObjectDTO) {
        String key = redisObjectDTO.getKey();
        return valueOperations.get(key);
    }

    public Long increment(RedisObjectDTO redisObjectDTO) {
        String key = redisObjectDTO.getKey();
        return valueOperations.increment(key, Long.valueOf(redisObjectDTO.getValue().toString()));
    }

    public void deleteKey(RedisKeyDTO redisKeyDTO) {
        String key = redisKeyDTO.getKey();
        redisTemplate.delete(key);
    }

    public Long getExpire(RedisKeyDTO redisKeyDTO) {
        String key = redisKeyDTO.getKey();
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    public void expireKey(RedisExpireDTO redisExpireDTO) {
        String key = redisExpireDTO.getKey();
        redisTemplate.expire(key, redisExpireDTO.getTimeout(), TimeUnit.SECONDS);
    }

    public void setHashAll(RedisHashDTO redisHashDTO) {
        String key = redisHashDTO.getKey();
        Map<String, String> value = redisHashDTO.getValue();
        hashOperations.putAll(key, value);
        if (redisHashDTO.getTimeout() != null) {
            redisTemplate.expire(key, redisHashDTO.getTimeout(), TimeUnit.SECONDS);
        }
    }

    public Map<String, String> getHashAll(RedisHashDTO redisHashDTO) {
        String key = redisHashDTO.getKey();
        return hashOperations.entries(key);
    }

    public Set<String> getHashKeys(RedisHashDTO redisHashDTO) {
        String key = redisHashDTO.getKey();
        return hashOperations.keys(key);
    }

    public List<String> getHashValues(RedisHashDTO redisHashDTO) {
        String key = redisHashDTO.getKey();
        return hashOperations.values(key);
    }

    public boolean hashHasKey(RedisHashFieldDTO redisHashFieldDTO) {
        String key = redisHashFieldDTO.getKey();
        return hashOperations.hasKey(key, redisHashFieldDTO.getHashKey());
    }

    public void setHashField(RedisHashFieldDTO redisHashFieldDTO) {
        String key = redisHashFieldDTO.getKey();
        hashOperations.put(key, redisHashFieldDTO.getHashKey(), redisHashFieldDTO.getFieldValue());
    }

    public String getHashField(RedisHashFieldDTO redisHashFieldDTO) {
        String key = redisHashFieldDTO.getKey();
        return hashOperations.get(key, redisHashFieldDTO.getHashKey());
    }

    public void deleteHashField(RedisHashFieldDTO redisHashFieldDTO) {
        String key = redisHashFieldDTO.getKey();
        hashOperations.delete(key, redisHashFieldDTO.getHashKey());
    }

    public void setListAll(RedisListDTO redisListDTO) {
        String key = redisListDTO.getKey();
        listOperations.rightPushAll(key, redisListDTO.getValue());
        if (redisListDTO.getTimeout() != null) {
            redisTemplate.expire(key, redisListDTO.getTimeout(), TimeUnit.SECONDS);
        }
    }

    public void setListValue(RedisValueDTO redisValueDTO) {
        String key = redisValueDTO.getKey();
        listOperations.rightPush(key, redisValueDTO.getValue());
    }

    public List<String> getListAll(RedisListDTO redisListDTO) {
        String key = redisListDTO.getKey();
        return listOperations.range(key, 0, -1);
    }

    public void setSetAll(RedisSetDTO redisSetDTO) {
        String key = redisSetDTO.getKey();
        setOperations.add(key, redisSetDTO.getValue().toArray(new String[]{}));
        if (redisSetDTO.getTimeout() != null) {
            redisTemplate.expire(key, redisSetDTO.getTimeout(), TimeUnit.SECONDS);
        }
    }

    public void setSetValue(RedisValueDTO redisValueDTO) {
        String key = redisValueDTO.getKey();
        setOperations.add(key, redisValueDTO.getValue());
    }

    public Set<String> getSetAll(RedisSetDTO redisSetDTO) {
        String key = redisSetDTO.getKey();
        return setOperations.members(key);
    }

    public void removeSetValue(RedisValueDTO redisValueDTO) {
        String key = redisValueDTO.getKey();
        setOperations.remove(key, redisValueDTO.getValue());
    }

}
