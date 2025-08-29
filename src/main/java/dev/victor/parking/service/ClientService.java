package dev.victor.parking.service;

import dev.victor.parking.controller.dto.ClientRequestDto;
import dev.victor.parking.entity.Client;
import dev.victor.parking.exception.ClientNotFoundException;
import dev.victor.parking.repository.ClientRepository;
import dev.victor.parking.service.dto.ClientResponseDto;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public ClientResponseDto create(ClientRequestDto dto) {
        Client client = dto.toEntity();
        Client savedClient = clientRepository.save(client);
        return ClientResponseDto.toDto(savedClient);
    }

    public ClientResponseDto findById(Long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException(String.format("Client with id: %d not found", id)));
        return ClientResponseDto.toDto(client);
    }
}
