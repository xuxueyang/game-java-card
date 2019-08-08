package itemmanager.respository;

import core.core.BaseRepository;
import itemmanager.domain.battle.Card;
import itemmanager.domain.battle.Deck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeckRepository extends BaseRepository<Deck,Long> {
    void deleteAllByDeckIdAndUserId(String deckId, Long id);

    List<Deck> findAllByDeckIdAndUserId(String deckId, Long userId);

    List<Deck> findAllByUserId(Long userId);

    @Modifying
    @Query(nativeQuery = true,value = "" +
            "update t_deck set t_deck.actived = 1 where t_deck.userId = ?1 and t_deck.deck_id = ?2;" +
            "update t_deck set t_deck.actived = 0 where t_deck.userId = ?1 and t_deck.deck_id != ?2;" +
            "")
    void deckRepository(Long userId, String  deckId);


    @Query(nativeQuery = true,value = "select deck.id from t_deck deck where deck.userId =?1 and deck.actived = ?2 limit 1")
    String findAllByDeckIdAndActived(Long deckId,Long actived);

    List<Deck> findAllByDeckId(String deckId);
}
