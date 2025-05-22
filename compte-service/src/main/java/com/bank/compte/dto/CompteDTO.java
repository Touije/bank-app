package com.bank.compte.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompteDTO {
    private Long id;
    private String numeroCompte;
    private BigDecimal solde;
    private String typeCompte;
    private Long clientId;
    private boolean actif;
} 