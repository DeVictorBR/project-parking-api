package dev.victor.parking.controller;

import dev.victor.parking.controller.dto.ClientRequestDto;
import dev.victor.parking.service.ClientService;
import dev.victor.parking.service.dto.ClientResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(path = "/clients")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping
    public ResponseEntity<ClientResponseDto> create(@RequestBody ClientRequestDto dto) {
        ClientResponseDto responseDto = clientService.create(dto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(responseDto.clientId())
                .toUri();
        return ResponseEntity.created(location).body(responseDto);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ClientResponseDto> findById(@PathVariable Long id) {
        ClientResponseDto responseDto = clientService.findById(id);
        return ResponseEntity.ok(responseDto);
    }
}
