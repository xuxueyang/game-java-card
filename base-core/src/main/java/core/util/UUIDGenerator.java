package core.util;

import java.util.UUID;

public class UUIDGenerator {
    public UUIDGenerator() {
    }

    /**
     * �Զ�����32λ��UUid����Ӧ���ݿ������id���в����á�
     * @return
     */
    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
