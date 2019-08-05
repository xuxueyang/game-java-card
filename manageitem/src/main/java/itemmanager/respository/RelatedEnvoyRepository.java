package itemmanager.respository;

import core.core.BaseRepository;
import itemmanager.domain.battle.Card;
import itemmanager.domain.battle.RelatedEnvoy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RelatedEnvoyRepository extends BaseRepository<RelatedEnvoy,Long> {
    RelatedEnvoy findOneByUserIdAndEnvoyId(Long userId, Long id);

    List<RelatedEnvoy> findAllByUserId(Long userId);
}
