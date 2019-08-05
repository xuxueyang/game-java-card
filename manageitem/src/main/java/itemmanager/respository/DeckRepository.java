package itemmanager.respository;

import core.core.BaseRepository;
import itemmanager.domain.battle.Card;
import itemmanager.domain.battle.Deck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeckRepository extends BaseRepository<Deck,Long> {
    void deleteAllByDeckIdAndUserId(String deckId, Long id);

    List<Deck> findAllByDeckIdAndUserId(String deckId, Long userId);

    List<Deck> findAllByUserId(Long userId);
}
