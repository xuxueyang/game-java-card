package itemmanager.service;


import core.dto.item.dto.CardDTO;
import core.rpc.dto.CardRpcDTO;
import core.rpc.dto.DeckRpcDTO;
import core.rpc.dto.EnvoyRpcDTO;
import core.util.UUIDGenerator;
import dist.ItemConstants;
import itemmanager.domain.battle.Card;
import itemmanager.domain.battle.Deck;
import itemmanager.domain.battle.Envoy;
import itemmanager.domain.battle.RelatedEnvoy;
import itemmanager.dto.SaveDeckDTO;
import itemmanager.respository.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class DeckService {
    @Autowired
    private DeckRepository deckRepository;
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private EnvoyRepository envoyRepository;
    @Autowired
    private RelatedEnvoyRepository relatedEnvoyRepository;
    @Autowired
    private EffectRepository effectRepository;

    @Transactional
    public void delete(Long userId, String deckId) {
        deckRepository.deleteAllByDeckIdAndUserId(deckId,userId);
    }


    @Transactional
    public String save(Long userId, List<SaveDeckDTO> saveDeckDTO) {
        String deckId = UUIDGenerator.getUUID();
        List<Deck> decks = new ArrayList<>();
        try {
            if(saveDeckDTO!=null){
                for(int i=0;i<saveDeckDTO.size();i++){
                    SaveDeckDTO deckDTO = saveDeckDTO.get(i);
                    if(checkSaveDeckDTO(deckDTO)){
                        //TODO 校驗合格，存儲
                        Deck deck = new Deck();
                        deck.setDeckId(deckId);
//                        deck.setLikeType(deckDTO.getLikeType());
                        deck.setType(deckDTO.getType());
                        deck.setRelatedId(deckDTO.getRelatedId());
                        deck.setUserId(userId);
                        decks.add(deck);
                    }else{
                        throw new Exception("校驗不合格");
                    }
                }
            }
        }catch (Exception e){
            return "";
        }
        //校驗穿的卡牌和棋子
        for (Deck deck : decks) {
            deckRepository.save(deck);
        }
        return deckId;
    }
    private boolean checkSaveDeckDTO(SaveDeckDTO deckDTO){
        //字段範圍對不對
        //卡牌ID和棋子ID存在不存在
        //TODO 以後是要校驗關聯的，現在就直接校驗吧--

        return true;
    }

    public Map<String,List<SaveDeckDTO>> get(Long id, String deckId) {
        if(deckId!=null&&!"".equals(deckId)){
            List<Deck> deck =  deckRepository.findAllByDeckIdAndUserId(deckId,id);
            HashMap<String, List<SaveDeckDTO>> stringListHashMap = new HashMap<>();
            List<SaveDeckDTO> deckDTOS = new ArrayList<>();
            if(deck!=null){
                deck.forEach(entity -> {
                    SaveDeckDTO e = transferTo(entity);
                    if(e!=null)
                        deckDTOS.add(e);
                });
            }
            stringListHashMap.put(deckId,deckDTOS);
            return stringListHashMap;
        }else{
            //查詢出全部的deckId，根據userId分組
            List<Deck> deck =  deckRepository.findAllByUserId(id);
            Set<String> deckIdSet = new HashSet<>();
            HashMap<String, List<SaveDeckDTO>> hashMap = new HashMap<>();

            if(deck!=null){
                deck.forEach(entity -> {
                    List<SaveDeckDTO> deckDTOS = null;
                    if(deckIdSet.contains(entity.getDeckId())){
                        deckDTOS = hashMap.get(entity.getDeckId());
                    }else{
                        deckDTOS = new ArrayList<>();
                        hashMap.put(entity.getDeckId(),deckDTOS);
                    }
                    SaveDeckDTO e = transferTo(entity);
                    if(e!=null)
                        deckDTOS.add(e);
                });
            }
            return hashMap;
        }
    }
    private SaveDeckDTO transferTo(Deck deck){
        if(deck==null)
            return null;
        if(ItemConstants.Type.CARD.equals(deck.getType())&&ItemConstants.Type.ENVOY.equals(deck.getType())){
//            if(ItemConstants.likeType.ATTACK.equals(deck.getLikeType())
//                    &&ItemConstants.likeType.Defense.equals(deck.getLikeType())
//                    &&ItemConstants.likeType.Blood.equals(deck.getLikeType())){
                SaveDeckDTO deckDTO = new SaveDeckDTO();
//                deckDTO.setLikeType(deck.getLikeType());
                deckDTO.setType(deck.getType());
                deckDTO.setRelatedId(deck.getRelatedId());
//            }
        }
        return null;
    }

    public void deckSetActived(Long userId, String deckId) {
        deckRepository.deckRepository(userId,deckId);
    }

    public DeckRpcDTO getDeckConfigById(String deckId) {
        DeckRpcDTO deckRpcDTO = new DeckRpcDTO();
        deckRpcDTO.setDeckId(deckId);
        List<Deck> deckList = deckRepository.findAllByDeckId(deckId);
        //轉換卡組為需要的戰鬥數據
        List<EnvoyRpcDTO> envoyRpcDTOS = new ArrayList<>(3);
        List<CardRpcDTO> cardRpcDTOS = new ArrayList<>(20);
        for(Deck deck:deckList){
            String type = deck.getType().name();
            if(ItemConstants.Type.CARD.name().equals(type)){
                CardRpcDTO cardRpcDTO = new CardRpcDTO();
                cardRpcDTO.setId(UUIDGenerator.getUUID());
                cardRpcDTO.setMetaCardId(deck.getRelatedId());
                Optional<Card> byId = cardRepository.findById(deck.getRelatedId());
                Card one = byId.isPresent()?byId.get():null;
                if(one!=null){
                    cardRpcDTO.setStartForce(one.getStarForce());
                    cardRpcDTO.setCardType(ItemConstants.getCardTypeByCode(one.getType()));
//                    cardRpcDTO.setEffectId(effectRepository.findOne(one.getEffectId()).getId());
                    cardRpcDTOS.add(cardRpcDTO);
                }

            }else if(ItemConstants.Type.ENVOY.name().equals(type)){
                EnvoyRpcDTO envoyRpcDTO = new EnvoyRpcDTO();
                envoyRpcDTO.setId(UUIDGenerator.getUUID());
                Optional<Envoy> byId = envoyRepository.findById(deck.getRelatedId());
                Envoy one = byId.isPresent()?byId.get():null;
                BeanUtils.copyProperties(one,envoyRpcDTO);
                RelatedEnvoy relatedEnvoy = relatedEnvoyRepository.findOneByUserIdAndEnvoyId(deck.getUserId(), deck.getRelatedId());
//                envoyRpcDTO.setAttack(one.getAttack()
//                        +relatedEnvoy.getLevel()*one.getIncrAttack()+relatedEnvoy.getPlusAttack());
//                envoyRpcDTO.setDefense(one.getDefense()
//                        +one.getIncrDefense()*relatedEnvoy.getLevel()+relatedEnvoy.getPlusDefense());
//                envoyRpcDTO.setHp(one.getHp()
//                        +one.getIncrHp()*relatedEnvoy.getLevel()+relatedEnvoy.getPlusHp());
                envoyRpcDTO.setAttackDistance(one.getAttackDistance());
                envoyRpcDTO.setAttribute(ItemConstants.getAttributeByCode(one.getAttribute()));
                envoyRpcDTO.setCriticalRate(one.getCriticalRate());

                envoyRpcDTO.setGrade(ItemConstants.getGradeByCode(one.getGrade()));

                envoyRpcDTO.setMove(one.getMove());
//                envoyRpcDTO.setRace(ItemConstants.getRaceByCode(one.getRace()));

                envoyRpcDTOS.add(envoyRpcDTO);
            }
        }

        return deckRpcDTO;
    }

    public String findActivedDeckByUserId(Long userId) {
        return deckRepository.findAllByDeckIdAndActived(userId, 1L);
    }
}
