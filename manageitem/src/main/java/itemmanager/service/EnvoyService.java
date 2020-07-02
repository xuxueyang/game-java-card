package itemmanager.service;

import core.dto.item.dto.EnvoyDTO;
import core.dto.item.dto.RelatedEnvoyDTO;
import dist.ItemConstants;
import itemmanager.domain.battle.Envoy;
import itemmanager.domain.battle.RelatedEnvoy;
import itemmanager.respository.EnvoyRepository;
import itemmanager.respository.RelatedEnvoyRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class EnvoyService {
    @Autowired
    private EnvoyRepository envoyRepository;
    @Autowired
    private RelatedEnvoyRepository relatedEnvoyRepository;


    public List<RelatedEnvoyDTO> findAllByUserId(Long userId) {
        List<RelatedEnvoyDTO> dtos = new ArrayList<>();
        relatedEnvoyRepository.findAllByUserId(userId).forEach(relatedEnvoy -> {
            dtos.add(transferTo(relatedEnvoy));
        });
        return dtos;
    }

    public List<EnvoyDTO> findAll() {
        List<EnvoyDTO> dtos = new ArrayList<>();
        envoyRepository.findAll().forEach(envoy -> {
            if(envoy.getIsDeleted()==0)
                dtos.add(transferTo(envoy));
        });
        return dtos;
    }
    private RelatedEnvoyDTO transferTo(RelatedEnvoy relatedCard){
        RelatedEnvoyDTO dto = new RelatedEnvoyDTO();
        BeanUtils.copyProperties(relatedCard,dto);
        return dto;
    }
    private EnvoyDTO transferTo(Envoy envoy){
        EnvoyDTO dto = new EnvoyDTO();
        BeanUtils.copyProperties(envoy,dto);
        //设置种族、品级、属性、
        dto.setGradeName(ItemConstants.getGradeByCode(envoy.getGrade()).getName());
        dto.setAttributeName(ItemConstants.getAttributeByCode(envoy.getAttribute()).getName());
//        dto.setRaceName(ItemConstants.getRaceByCode(envoy.getRace()).getName());

        return dto;
    }

    public EnvoyDTO findOne(Long id) {
        Optional<Envoy> byId = envoyRepository.findById(id);
//        one.setGradeName(ItemConstants.getGradeByCode(one.getGrade()).getName());
//        one.setStarForce(ItemConstants.getGradeByCode(one.getGrade()).getStarForce());
        if(byId.isPresent())
            return transferTo(byId.get());
        return null;
    }

    public List<EnvoyDTO> findAllAdmin() {
        List<Envoy> all = envoyRepository.findAll();
        List<EnvoyDTO> re = new LinkedList<>();
        all.forEach(envoy -> {
            re.add(transferTo(envoy));
        });
        return re;
    }
}
