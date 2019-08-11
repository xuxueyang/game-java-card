package itemmanager.service;

import com.alibaba.fastjson.JSON;
import dist.ItemConstants;
import itemmanager.domain.battle.*;
import itemmanager.dto.AdminUpdateCardDTO;
import itemmanager.dto.AdminUpdateEnvoyDTO;
import itemmanager.respository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import util.MyBeanUtils;

import java.util.ArrayList;
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
            if(one!=null){
                MyBeanUtils.copyPropertiesExcludeNull(adminUpdateCard,one);
                if(adminUpdateCard.getJsonMap()!=null){
                    one.setJsonMap(JSON.toJSONString(adminUpdateCard.getJsonMap()));
                }
            }

            else
                throw new Exception("不存在");
        }else{
            Card one = new Card();
            MyBeanUtils.copyPropertiesExcludeNull(adminUpdateCard,one);
            if(adminUpdateCard.getJsonMap()!=null){
                one.setJsonMap(JSON.toJSONString(adminUpdateCard.getJsonMap()));
            }
            cardRepository.save(one);
        }

    }
    public void adminChangeEnvoy(AdminUpdateEnvoyDTO adminUpdateEnvoy)  throws Exception {
        //修改棋子属性（仅测试）
        if(adminUpdateEnvoy.getId()!=null){
            Envoy one = envoyRepository.findOne(adminUpdateEnvoy.getId());
            if(one!=null){
                MyBeanUtils.copyPropertiesExcludeNull(adminUpdateEnvoy,one);
                envoyRepository.save(one);
            }
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
        {
            ItemConstants.CardType[] values = ItemConstants.CardType.values();
            List<Map> mapList = new ArrayList<>();
            for(int i=0;i<values.length;i++){
                Map<String,String> tmp = new HashMap<>();
                tmp.put("label",values[i].getName());
                tmp.put("value",values[i].getCode());
                mapList.add(tmp);
            }
            map.put("CardType",mapList);
        }
        {
            ItemConstants.Grade[] values = ItemConstants.Grade.values();
            List<Map> mapList = new ArrayList<>();
            for(int i=0;i<values.length;i++){
                Map<String,String> tmp = new HashMap<>();
                tmp.put("label",values[i].getName());
                tmp.put("value","" + values[i].getIndex());
                mapList.add(tmp);
            }
            map.put("Grade",mapList);
        }
        {
            ItemConstants.Attribute[] values = ItemConstants.Attribute.values();
            List<Map> mapList = new ArrayList<>();
            for(int i=0;i<values.length;i++){
                Map<String,String> tmp = new HashMap<>();
                tmp.put("label",values[i].getName());
                tmp.put("value",""+values[i].getIndex());
                mapList.add(tmp);
            }
            map.put("Attribute",mapList);
        }
        {
            ItemConstants.Race[] values = ItemConstants.Race.values();
            List<Map> mapList = new ArrayList<>();
            for(int i=0;i<values.length;i++){
                Map<String,String> tmp = new HashMap<>();
                tmp.put("label",values[i].getName());
                tmp.put("value",""+values[i].getIndex());
                mapList.add(tmp);
            }
            map.put("Race",mapList);
        }
        List<Effect> all = effectRepository.findAll();
        map.put("Effect",all);
        {
            Map<String,Double> tmp = new HashMap<>();
            //    1星辰力=14血量=4成长生命值=4基础攻击力=1成长攻击力=0.2移动力=0.1攻击距离=3防御=0.5成长防御，放到战斗房间中计算？
            tmp.put("hp",14.0);
            tmp.put("incrHp",4.0);
            tmp.put("attack",4.0);
            tmp.put("incrAttack",1.0);
            tmp.put("defense",3.0);
            tmp.put("incrDefense",0.5);
            tmp.put("move",0.2);
            tmp.put("attackDistance",0.1);

            map.put("numTransfer",tmp);
        }
        return map;
    }
    //计算星辰值，换算单位
    //    1星辰力=14血量=4成长生命值=4基础攻击力=1成长攻击力=0.2移动力=0.1攻击距离=3防御=0.5成长防御，放到战斗房间中计算？

}
