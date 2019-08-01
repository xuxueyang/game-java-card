package core.spring.MQ;

import core.core.RequestDTO;
import core.inter.MQFirstInterface;

import java.util.List;

public  class SpringMQFirstService implements MQFirstInterface {
    public RequestDTO dto = null;
    @Override
    public RequestDTO getObjectByKey(Byte firstType) {
        return dto;
    }

    @Override
    public RequestDTO pollObjectByKey(Byte firstType) {
        RequestDTO dto = this.dto;
        this.dto = null;
        return dto;
    }

    @Override
    public List<RequestDTO> peekObjectsByKey(Byte firstType) {

        return null;
    }

    @Override
    public List<RequestDTO> peekObjectsByKeyLimit(Byte firstType, int num) {
        return null;
    }


    @Override
    public void putObjectByType(Byte firstType,RequestDTO dto) {
        this.dto = dto;
    }

    @Override
    public RequestDTO deleteByKey(Byte type) {
        return null;
    }

    @Override
    public List<RequestDTO> deleteAll(RequestDTO type) {
        return null;
    }

    @Override
    public List<Byte> getKeys() {
        return null;
    }


}