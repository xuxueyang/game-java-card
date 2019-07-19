package util.check;

import java.util.UUID;

public final class UUIDGenerator {
    public UUIDGenerator() {
    }

    /**
     * 自动生成32位的UUid，对应数据库的主键id进行插入用。
     * @return
     */
    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
