package com.bank.compte.controller;

import com.bank.compte.dto.CompteDTO;
import com.bank.compte.service.CompteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/comptes")
@RequiredArgsConstructor
public class CompteController {
    private final CompteService compteService;

    @PostMapping
    public ResponseEntity<CompteDTO> createCompte(@RequestBody CompteDTO compteDTO) {
        return ResponseEntity.ok(compteService.createCompte(compteDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompteDTO> getCompte(@PathVariable Long id) {
        return ResponseEntity.ok(compteService.getCompte(id));
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<CompteDTO>> getComptesByClientId(@PathVariable Long clientId) {
        return ResponseEntity.ok(compteService.getComptesByClientId(clientId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CompteDTO> updateCompte(@PathVariable Long id, @RequestBody CompteDTO compteDTO) {
        return ResponseEntity.ok(compteService.updateCompte(id, compteDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompte(@PathVariable Long id) {
        compteService.deleteCompte(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/crediter")
    public ResponseEntity<CompteDTO> crediter(
            @PathVariable Long id,
            @RequestParam BigDecimal montant) {
        return ResponseEntity.ok(compteService.crediter(id, montant));
    }

    @PostMapping("/{id}/debiter")
    public ResponseEntity<CompteDTO> debiter(
            @PathVariable Long id,
            @RequestParam BigDecimal montant) {
        return ResponseEntity.ok(compteService.debiter(id, montant));
    }
} 