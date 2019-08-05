package itemmanager.service;


import core.util.UUIDGenerator;
import itemmanager.domain.battle.Deck;
import itemmanager.dto.SaveDeckDTO;
import itemmanager.respository.DeckRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class DeckService {
    @Autowired
    private DeckRepository deckRepository;


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
                        deck.setLikeType(deckDTO.getLikeType());
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
        deckRepository.save(decks);
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
        if(Deck.Type.CARD.equals(deck.getType())&&Deck.Type.ENVOY.equals(deck.getType())){
            if(Deck.likeType.ATTACK.equals(deck.getLikeType())
                    &&Deck.likeType.Defense.equals(deck.getLikeType())
                    &&Deck.likeType.Blood.equals(deck.getLikeType())){
                SaveDeckDTO deckDTO = new SaveDeckDTO();
                deckDTO.setLikeType(deck.getLikeType());
                deckDTO.setType(deck.getType());
                deckDTO.setRelatedId(deck.getRelatedId());
            }
        }
        return null;
    }
}
