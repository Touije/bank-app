package com.bank.compte.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
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
    private String emailClient;
    private String rib;
    private String codeCarte;
} 