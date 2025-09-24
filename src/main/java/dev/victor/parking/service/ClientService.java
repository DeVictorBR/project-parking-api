package dev.victor.parking.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import dev.victor.parking.client.dto.CustomerDataRequestDto;
import dev.victor.parking.controller.dto.ClientRequestDto;
import dev.victor.parking.entity.Client;
import dev.victor.parking.exception.ClientNotFoundException;
import dev.victor.parking.exception.DuplicateDataException;
import dev.victor.parking.exception.PatchException;
import dev.victor.parking.repository.ClientRepository;
import dev.victor.parking.service.dto.ClientResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClientService {

    private final ClientRepository clientRepository;
    private final ObjectMapper objectMapper;
    private final CustomerClientService customerClientService;


    public ClientService(ClientRepository clientRepository, ObjectMapper objectMapper, CustomerClientService customerClientService) {
        this.clientRepository = clientRepository;
        this.objectMapper = objectMapper;
        this.customerClientService = customerClientService;
    }

    public ClientResponseDto create(ClientRequestDto dto) {
        List<String> duplicateFields = new ArrayList<>();
        if (clientRepository.findByCpf(dto.cpf()).isPresent()) {
            duplicateFields.add("cpf");
        }
        if (clientRepository.findByEmail(dto.email()).isPresent()) {
            duplicateFields.add("email");
        }
        if (!duplicateFields.isEmpty()) {
            throw new DuplicateDataException("Some fields already exist.", duplicateFields);
        }

        Client client = dto.toEntity();
        Client savedClient = clientRepository.save(client);
        var response = customerClientService.createCustomer(new CustomerDataRequestDto(
                client.getName(),
                client.getPhoneNumber(),
                client.getEmail(),
                client.getCpf()
        ));
        System.out.println(response);
        return ClientResponseDto.toDto(savedClient);
    }

    public ClientResponseDto findById(Long id) {
        Client client = getClientById(id);
        return ClientResponseDto.toDto(client);
    }

    public Page<ClientResponseDto> findAll(Pageable pageable) {
        Page<Client> clientsPage = clientRepository.findAll(pageable);
        return clientsPage.map(ClientResponseDto::toDto);
    }

    public ClientResponseDto updatePartial(Long id, JsonPatch patch) {
        try {
            Client client = getClientById(id);
            JsonNode patched = patch.apply(objectMapper.convertValue(client, JsonNode.class));
            Client updatedClient = objectMapper.treeToValue(patched, Client.class);
            return ClientResponseDto.toDto(clientRepository.save(updatedClient));
        } catch (JsonPatchException | JsonProcessingException e) {
            throw new PatchException("Failed to apply patch due to invalid document or malformed data");
        }
    }

    public void delete(Long id) {
        Client existClient = getClientById(id);
        clientRepository.delete(existClient);
    }

    protected Client getClientById(Long id) {
         return clientRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException(String.format("Client with id: %d not found", id)));
    }
}
