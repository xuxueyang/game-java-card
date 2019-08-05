package itemmanager.respository;

import core.core.BaseRepository;
import itemmanager.domain.battle.Card;
import itemmanager.domain.battle.Envoy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnvoyRepository extends BaseRepository<Envoy,Long> {
}
