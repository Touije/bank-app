package com.bank.compte.repository;

import com.bank.compte.entities.Compte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CompteRepository extends JpaRepository<Compte, Long> {
    List<Compte> findByClientId(Long clientId);
    boolean existsByNumeroCompte(String numeroCompte);
} 