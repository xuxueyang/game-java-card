package roommanager.service.map;

public class GameMapFactory {
    public static GameMap getGameMapById(Integer mapId){
        return new DefaultGameMap();
    }
}
