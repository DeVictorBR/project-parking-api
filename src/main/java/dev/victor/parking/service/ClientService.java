package dev.victor.parking.service;

import dev.victor.parking.controller.dto.ClientRequestDto;
import dev.victor.parking.entity.Client;
import dev.victor.parking.repository.ClientRepository;
import dev.victor.parking.service.dto.ClientResponseDto;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public ClientResponseDto createClient(ClientRequestDto dto) {
        Client client = dto.toEntity();
        Client savedClient = clientRepository.save(client);
        return ClientResponseDto.toDto(savedClient);
    }
}
