package roommanager.service.map;

import core.rpc.dto.EnvoyRpcDTO;

import java.util.List;

public  abstract class GameMap {
    public int xLength = 10;
    public int yLength = 10;
    public abstract void initPosition(List<EnvoyRpcDTO> dtos,int index);

    public int getxLength() {
        return xLength;
    }

    public void setxLength(int xLength) {
        this.xLength = xLength;
    }

    public int getyLength() {
        return yLength;
    }

    public void setyLength(int yLength) {
        this.yLength = yLength;
    }
}
