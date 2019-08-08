package itemmanager.service;

import dist.ItemConstants;
import itemmanager.domain.battle.*;
import itemmanager.dto.AdminUpdateCardDTO;
import itemmanager.dto.AdminUpdateEnvoyDTO;
import itemmanager.respository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import util.MyBeanUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AdminService  {
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private EnvoyRepository envoyRepository;
    @Autowired
    private RelatedCardRepository relatedCardRepository;
    @Autowired
    private RelatedEnvoyRepository relatedEnvoyRepository;
    @Autowired
    private EffectRepository effectRepository;

    //解鎖一個用戶下所有卡牌和妻子
    public void unLockAll(Long userId){
        List<Card> allCard = cardRepository.findAll();
        List<Envoy> allEnvoy = envoyRepository.findAll();
//        relatedCardRepository.deleteAllByUserId(userId);
        if(allCard!=null){
            for(Card card:allCard){
                RelatedCard oneByUserIdAndCardId = relatedCardRepository.findOneByUserIdAndCardId(userId, card.getId());
                if(oneByUserIdAndCardId==null){
                    RelatedCard relatedCard = new RelatedCard();
                    relatedCard.setCardId(card.getId());
                    relatedCard.setUserId(userId);
                    relatedCardRepository.save(relatedCard);
                }
            }
        }
        if(allEnvoy!=null){
            for(Envoy envoy:allEnvoy){
                RelatedEnvoy oneByUserIdAndEnvoyId = relatedEnvoyRepository.findOneByUserIdAndEnvoyId(userId, envoy.getId());
                if(oneByUserIdAndEnvoyId==null){
                    //添加這個棋子
                    RelatedEnvoy relatedEnvoy = new RelatedEnvoy();
                    relatedEnvoy.setEnvoyId(envoy.getId());
                    relatedEnvoy.setUserId(userId);
                    relatedEnvoyRepository.save(relatedEnvoy);
                }
            }
        }



    }

    public void adminChangeCard(AdminUpdateCardDTO adminUpdateCard) throws Exception {
        //修改棋子属性（仅测试）
        if(adminUpdateCard.getId()!=null){
            Card one = cardRepository.findOne(adminUpdateCard.getId());
            if(one!=null)
                MyBeanUtils.copyPropertiesExcludeNull(adminUpdateCard,one);
            else
                throw new Exception("不存在");
        }else{
            Card one = new Card();
            MyBeanUtils.copyPropertiesExcludeNull(adminUpdateCard,one);
            cardRepository.save(one);
        }

    }
    public void adminChangeEnvoy(AdminUpdateEnvoyDTO adminUpdateEnvoy)  throws Exception {
        //修改棋子属性（仅测试）
        if(adminUpdateEnvoy.getId()!=null){
            Envoy one = envoyRepository.findOne(adminUpdateEnvoy.getId());
            if(one!=null)
                MyBeanUtils.copyPropertiesExcludeNull(adminUpdateEnvoy,one);
            else
                throw new Exception("不存在");
        }else{
            Envoy one = new Envoy();
            MyBeanUtils.copyPropertiesExcludeNull(adminUpdateEnvoy,one);
            envoyRepository.save(one);
        }
    }


    public Map<String,Object> getAllConfig() {
        Map<String,Object> map = new HashMap<>();
        map.put("CardType",ItemConstants.CardType.values());
        map.put("Grade",ItemConstants.Grade.values());
        map.put("Attribute",ItemConstants.Attribute.values());
        map.put("Race",ItemConstants.Race.values());
        List<Effect> all = effectRepository.findAll();
        map.put("Effect",all);
        return map;
    }
    //计算星辰值，换算单位
    //    1星辰力=14血量=4成长生命值=4基础攻击力=1成长攻击力=0.2移动力=0.1攻击距离=3防御=0.5成长防御，放到战斗房间中计算？

}
