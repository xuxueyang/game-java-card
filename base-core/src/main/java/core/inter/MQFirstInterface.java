package core.inter;

import core.core.RequestDTO;

import java.util.List;

public interface MQFirstInterface {
    RequestDTO getObjectByKey(Byte firstType);
    RequestDTO pollObjectByKey(Byte firstType);

    List<RequestDTO> peekObjectsByKey(Byte firstType);
    List<RequestDTO> peekObjectsByKeyLimit(Byte firstType,int num);

    void putObjectByType(Byte firstType,RequestDTO object);

    RequestDTO deleteByKey(Byte type);
    List<RequestDTO> deleteAll(RequestDTO type);

    List<Byte> getKeys();

}
