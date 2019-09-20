package roommanager.service.map;

import core.rpc.dto.EnvoyRpcDTO;
import lombok.Data;

import java.util.List;

@Data
public  abstract class GameMap {
    public int xLength = 10;
    public int yLength = 10;
    public abstract void initPosition(List<EnvoyRpcDTO> dtos,int index);
}
