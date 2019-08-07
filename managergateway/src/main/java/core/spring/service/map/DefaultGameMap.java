package core.spring.service.map;

import core.rpc.dto.EnvoyRpcDTO;

import java.util.List;

public class DefaultGameMap extends GameMap {
    public DefaultGameMap() {
        //主玩家视角，左上为0,0，横为x
    }


    @Override
    public void initPosition(List<EnvoyRpcDTO> dtos, int index) {
        if(index==0){
            //主玩家视角，左上为0,0，横为x
            //主玩家,开始的0,4,9位初始化点，y：9
            Integer[] xArr = new Integer[]{0,4,9};
            for(int i=0;i<xArr.length;i++){
                dtos.get(i).setX(xArr[i]);
                dtos.get(i).setY(9);
            }

        }else{
            Integer[] xArr = new Integer[]{9,5,0};
            for(int i=0;i<xArr.length;i++){
                dtos.get(i).setX(xArr[i]);
                dtos.get(i).setY(0);
            }
        }
    }
}
