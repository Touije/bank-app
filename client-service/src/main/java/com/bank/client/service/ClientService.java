package com.bank.client.service;

import com.bank.client.dto.ClientDTO;
import com.bank.client.dto.events.ClientCreatedEvent;
import com.bank.client.entities.Client;
import com.bank.client.mapper.ClientMapper;
import com.bank.client.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ClientService {
    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;
    private final KafkaTemplate<String, ClientCreatedEvent> kafkaTemplate;
    private static final String TOPIC_CLIENT_CREATED = "client-created";

    public ClientDTO createClient(ClientDTO clientDTO) {
        if (clientRepository.existsByEmail(clientDTO.getEmail())) {
            throw new RuntimeException("Un client avec cet email existe déjà");
        }

        Client client = clientMapper.toEntity(clientDTO);
        client = clientRepository.save(client);
        
        // Publier l'événement de création
        ClientCreatedEvent event = new ClientCreatedEvent(
            client.getId(),
            client.getNom(),
            client.getPrenom(),
            client.getEmail()
        );
        kafkaTemplate.send(TOPIC_CLIENT_CREATED, event);

        return clientMapper.toDTO(client);
    }

    public ClientDTO getClient(Long id) {
        Client client = clientRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Client non trouvé"));
        return clientMapper.toDTO(client);
    }

    public List<ClientDTO> getAllClients() {
        return clientRepository.findAll().stream()
            .map(clientMapper::toDTO)
            .collect(Collectors.toList());
    }

    public ClientDTO updateClient(Long id, ClientDTO clientDTO) {
        Client existingClient = clientRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Client non trouvé"));

        // Vérifier si l'email est déjà utilisé par un autre client
        if (!existingClient.getEmail().equals(clientDTO.getEmail()) &&
            clientRepository.existsByEmail(clientDTO.getEmail())) {
            throw new RuntimeException("Un client avec cet email existe déjà");
        }

        Client client = clientMapper.toEntity(clientDTO);
        client.setId(id);
        client = clientRepository.save(client);
        return clientMapper.toDTO(client);
    }

    public void deleteClient(Long id) {
        if (!clientRepository.existsById(id)) {
            throw new RuntimeException("Client non trouvé");
        }
        clientRepository.deleteById(id);
    }
} 