package itemmanager.service;

import core.dto.item.dto.EnvoyDTO;
import core.dto.item.dto.RelatedEnvoyDTO;
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
    private EnvoyDTO transferTo(Envoy card){
        EnvoyDTO dto = new EnvoyDTO();
        BeanUtils.copyProperties(card,dto);
        return dto;
    }
}
