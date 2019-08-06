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
import java.util.List;

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
        //设置种族、品级、属性、
        envoy.setGradeName(ItemConstants.getGradeByCode(envoy.getGrade()).getName());
        envoy.setAttributeName(ItemConstants.getAttributeByCode(envoy.getAttribute()).getName());
        envoy.setRaceName(ItemConstants.getRaceByCode(envoy.getRace()).getName());

        BeanUtils.copyProperties(envoy,dto);
        return dto;
    }

    public EnvoyDTO findOne(Long id) {
        return transferTo(envoyRepository.findOne(id));
    }
}
