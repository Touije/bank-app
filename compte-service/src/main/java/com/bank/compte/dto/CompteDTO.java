package com.bank.compte.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompteDTO {
    private Long id;
    private String numeroCompte;
    private BigDecimal solde;
    private String typeCompte;
    private Long clientId;
    private boolean actif;
    private String emailClient;
    private String rib;
    private String codeCarte;
}
