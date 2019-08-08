package itemmanager.respository;

import core.core.BaseRepository;
import itemmanager.domain.battle.Effect;
import org.springframework.stereotype.Repository;

@Repository
public interface EffectRepository extends BaseRepository<Effect,Long> {
}
