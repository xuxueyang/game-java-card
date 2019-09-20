package roommanager.service.room.autochessroom;

import roommanager.service.room.autochessroom.ChessManager.MetaChessName;

import static roommanager.service.room.autochessroom.ChessManager.MetaChessName.GUAN_YU;

public class ChessFactory {
    public static Chess getChessByMetaId(String chessMetaId){
        return getDefault(ChessManager.getByName(chessMetaId));
    }
    private static Chess getDefault(MetaChessName metaChessName) {
        switch (metaChessName){
            case GUAN_YU:{
                return new Chess();
            }
            default:{
                return null;
            }
        }
    }
}
