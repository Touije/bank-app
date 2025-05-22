package com.bank.compte.mapper;

import com.bank.compte.dto.CompteDTO;
import com.bank.compte.entities.Compte;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CompteMapper {
    private static final Logger log = LoggerFactory.getLogger(CompteMapper.class);
    
    public CompteDTO toDTO(Compte compte) {
        if (compte == null) {
            log.warn("Tentative de conversion d'un compte null en DTO");
            return null;
        }
        
        CompteDTO dto = new CompteDTO();
        dto.setId(compte.getId());
        dto.setNumeroCompte(compte.getNumeroCompte());
        dto.setSolde(compte.getSolde());
        dto.setTypeCompte(compte.getTypeCompte());
        dto.setClientId(compte.getClientId());
        dto.setActif(compte.isActif());
        dto.setEmailClient(compte.getEmailClient());
        dto.setRib(compte.getRib());
        dto.setCodeCarte(compte.getCodeCarte());
        
        return dto;
    }
    
    public Compte toEntity(CompteDTO dto) {
        if (dto == null) {
            log.warn("Tentative de conversion d'un DTO null en entité");
            return null;
        }
        
        Compte compte = new Compte();
        compte.setId(dto.getId());
        compte.setNumeroCompte(dto.getNumeroCompte());
        compte.setSolde(dto.getSolde());
        compte.setTypeCompte(dto.getTypeCompte());
        compte.setClientId(dto.getClientId());
        compte.setActif(dto.isActif());
        compte.setEmailClient(dto.getEmailClient());
        compte.setRib(dto.getRib());
        compte.setCodeCarte(dto.getCodeCarte());
        
        return compte;
    }
} 