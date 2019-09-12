package core.dto.file;

import java.util.concurrent.ConcurrentHashMap;

public class CacheUtil {
    public ConcurrentHashMap<String,FileBurstInstruct> burstDataMap = new ConcurrentHashMap<>();

}
