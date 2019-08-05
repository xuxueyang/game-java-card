package itemmanager.respository;

import core.core.BaseRepository;
import core.dto.item.dto.RelatedCardDTO;
import itemmanager.domain.battle.Card;
import itemmanager.domain.battle.RelatedCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RelatedCardRepository extends BaseRepository<RelatedCard,Long> {
    void deleteAllByUserId(Long userId);

    RelatedCard findOneByUserIdAndCardId(Long userId, Long id);

    List<RelatedCard> findAllByUserId(Long userId);
}
