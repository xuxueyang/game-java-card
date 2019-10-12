package roommanager.service.room.autochessroom;

import core.protocol.AutoChessRoomProtocol;
import lombok.Data;

@Data
public class Skill {
    private AutoChessRoomProtocol.SkillType type;
    public Skill(AutoChessRoomProtocol.SkillType type) {
        this.type = type;
    }

    public static Skill getSkillById(int skillId) {
        return new Skill(AutoChessRoomProtocol.SkillType.BEI_DONG);
    }
}
