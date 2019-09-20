package roommanager.service.room.autochessroom;

import lombok.Data;

@Data
public class NodeManager {
    public enum NodeManagerType{
        SELF,//自己的
        BATTLE,//临时战斗棋盘
    }

    private static final int xLength = 8;
    private static final int yLength = 8;

    private  Node[][] nodes = new Node[xLength][yLength];

    public NodeManager(NodeManagerType type){
        for(int j=0;j<yLength;j++){
            for(int i=0;i<xLength;i++){
                nodes[i][j] = new Node(i,j);
                if(type.equals(NodeManagerType.BATTLE)&&i<xLength/2){
                    nodes[i][j].canUse = false;
                }
            }
        }
    }
    @Data
    public class Node{
        private int x;
        private int y;
        private boolean canUse = true;
        public Node(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
