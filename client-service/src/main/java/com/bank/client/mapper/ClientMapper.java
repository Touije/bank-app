package com.bank.client.mapper;

import com.bank.client.dto.ClientDTO;
import com.bank.client.entities.Client;
import org.springframework.stereotype.Component;

@Component
public class ClientMapper {
    
    public ClientDTO toDTO(Client client) {
        if (client == null) return null;
        
        return new ClientDTO(
            client.getId(),
            client.getNom(),
            client.getPrenom(),
            client.getEmail(),
            client.getTelephone(),
            client.getAdresse()
        );
    }
    
    public Client toEntity(ClientDTO dto) {
        if (dto == null) return null;
        
        Client client = new Client();
        client.setId(dto.getId());
        client.setNom(dto.getNom());
        client.setPrenom(dto.getPrenom());
        client.setEmail(dto.getEmail());
        client.setTelephone(dto.getTelephone());
        client.setAdresse(dto.getAdresse());
        
        return client;
    }
} 