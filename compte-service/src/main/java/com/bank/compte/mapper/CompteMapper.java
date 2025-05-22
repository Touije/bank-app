package com.bank.compte.mapper;

import com.bank.compte.dto.CompteDTO;
import com.bank.compte.entities.Compte;
import org.springframework.stereotype.Component;

@Component
public class CompteMapper {
    
    public CompteDTO toDTO(Compte compte) {
        if (compte == null) return null;
        
        return new CompteDTO(
            compte.getId(),
            compte.getNumeroCompte(),
            compte.getSolde(),
            compte.getTypeCompte(),
            compte.getClientId(),
            compte.isActif()
        );
    }
    
    public Compte toEntity(CompteDTO dto) {
        if (dto == null) return null;
        
        Compte compte = new Compte();
        compte.setId(dto.getId());
        compte.setNumeroCompte(dto.getNumeroCompte());
        compte.setSolde(dto.getSolde());
        compte.setTypeCompte(dto.getTypeCompte());
        compte.setClientId(dto.getClientId());
        compte.setActif(dto.isActif());
        
        return compte;
    }
} 