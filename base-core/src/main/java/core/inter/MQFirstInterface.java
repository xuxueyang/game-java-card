package core.inter;

import java.util.List;

public interface MQFirstInterface<Key,Value> {
    Value getObjectByKey(Key firstType);
    Value pollObjectByKey(Key firstType);

    List<Value> peekObjectsByKey(Key firstType);
    List<Value> peekObjectsByKeyLimit(Key firstType,int num);

    void putObjectByType(Key firstType,Value object);

    Value deleteByKey(Key type);
    List<Value> deleteAll(Key type);

    List<Key> getKeys();

}
