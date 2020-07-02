package itemmanager.service;

import core.dto.item.dto.CardDTO;
import core.dto.item.dto.RelatedCardDTO;
import itemmanager.domain.battle.Card;
import itemmanager.domain.battle.RelatedCard;
import itemmanager.respository.CardRepository;
import itemmanager.respository.RelatedCardRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CardService {
    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private RelatedCardRepository relatedCardRepository;

// cache
//   private static List<CardDTO> dtos = new ArrayList<>();

    public List<RelatedCardDTO> findAllByUserId(Long userId) {
        List<RelatedCardDTO> dtos = new ArrayList<>();
        relatedCardRepository.findAllByUserId(userId).forEach(relatedCard -> {
            dtos.add(transferTo(relatedCard));
        });
        return dtos;
    }

    public List<CardDTO> findAll() {
        List<CardDTO> dtos = new ArrayList<>();
        cardRepository.findAll().forEach(card -> {
            if(card.getIsDeleted()==0)
                dtos.add(transferTo(card));
        });
        return dtos;
    }
    private RelatedCardDTO transferTo(RelatedCard relatedCard){
        RelatedCardDTO dto = new RelatedCardDTO();
        BeanUtils.copyProperties(relatedCard,dto);
        return dto;
    }
    private CardDTO transferTo(Card card){
        CardDTO dto = new CardDTO();
        BeanUtils.copyProperties(card,dto);
        return dto;
    }

    public CardDTO findOne(Long id) {
        Optional<Card> byId = cardRepository.findById(id);
        if(byId.isPresent())
            return transferTo(byId.get());
        return null;
    }
}
