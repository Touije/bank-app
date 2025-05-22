package com.bank.compte.service;

import com.bank.compte.dto.CompteDTO;
import com.bank.compte.entities.Compte;
import com.bank.compte.mapper.CompteMapper;
import com.bank.compte.repository.CompteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CompteService {
    private final CompteRepository compteRepository;
    private final CompteMapper compteMapper;

    public CompteDTO createCompte(CompteDTO compteDTO) {
        if (compteRepository.existsByNumeroCompte(compteDTO.getNumeroCompte())) {
            throw new RuntimeException("Un compte avec ce numéro existe déjà");
        }

        Compte compte = compteMapper.toEntity(compteDTO);
        compte.setActif(true);
        compte.setSolde(BigDecimal.ZERO);
        compte = compteRepository.save(compte);
        return compteMapper.toDTO(compte);
    }

    public CompteDTO getCompte(Long id) {
        Compte compte = compteRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Compte non trouvé"));
        return compteMapper.toDTO(compte);
    }

    public List<CompteDTO> getComptesByClientId(Long clientId) {
        return compteRepository.findByClientId(clientId).stream()
            .map(compteMapper::toDTO)
            .collect(Collectors.toList());
    }

    public CompteDTO updateCompte(Long id, CompteDTO compteDTO) {
        Compte existingCompte = compteRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Compte non trouvé"));

        if (!existingCompte.getNumeroCompte().equals(compteDTO.getNumeroCompte()) &&
            compteRepository.existsByNumeroCompte(compteDTO.getNumeroCompte())) {
            throw new RuntimeException("Un compte avec ce numéro existe déjà");
        }

        Compte compte = compteMapper.toEntity(compteDTO);
        compte.setId(id);
        compte.setSolde(existingCompte.getSolde()); // Ne pas modifier le solde via l'update
        compte = compteRepository.save(compte);
        return compteMapper.toDTO(compte);
    }

    public void deleteCompte(Long id) {
        Compte compte = compteRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Compte non trouvé"));
        
        if (compte.getSolde().compareTo(BigDecimal.ZERO) != 0) {
            throw new RuntimeException("Impossible de supprimer un compte avec un solde non nul");
        }
        
        compteRepository.deleteById(id);
    }

    public CompteDTO crediter(Long id, BigDecimal montant) {
        if (montant.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Le montant doit être positif");
        }

        Compte compte = compteRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Compte non trouvé"));

        if (!compte.isActif()) {
            throw new RuntimeException("Le compte est inactif");
        }

        compte.setSolde(compte.getSolde().add(montant));
        compte = compteRepository.save(compte);
        return compteMapper.toDTO(compte);
    }

    public CompteDTO debiter(Long id, BigDecimal montant) {
        if (montant.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Le montant doit être positif");
        }

        Compte compte = compteRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Compte non trouvé"));

        if (!compte.isActif()) {
            throw new RuntimeException("Le compte est inactif");
        }

        if (compte.getSolde().compareTo(montant) < 0) {
            throw new RuntimeException("Solde insuffisant");
        }

        compte.setSolde(compte.getSolde().subtract(montant));
        compte = compteRepository.save(compte);
        return compteMapper.toDTO(compte);
    }
} 