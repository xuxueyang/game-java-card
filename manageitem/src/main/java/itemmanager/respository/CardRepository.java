package itemmanager.respository;

import core.core.BaseRepository;
import itemmanager.domain.battle.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends BaseRepository<Card,Long>,JpaRepository<Card,Long> {
}
