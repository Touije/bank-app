package com.bank.compte.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Compte {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String numeroCompte;
    private BigDecimal solde;
    private String typeCompte; // COURANT, EPARGNE
    private Long clientId;
    private boolean actif;
} 