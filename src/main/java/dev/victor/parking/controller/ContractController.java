package dev.victor.parking.controller;

import dev.victor.parking.controller.dto.ContractRequestDto;
import dev.victor.parking.service.ContractService;
import dev.victor.parking.service.dto.ContractResponseDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(path = "/contracts")
public class ContractController {

    private final ContractService contractService;

    public ContractController(ContractService contractService) {
        this.contractService = contractService;
    }

    @PostMapping
    public ResponseEntity<ContractResponseDto> create(@RequestBody @Valid ContractRequestDto dto) {
        ContractResponseDto responseDto = contractService.create(dto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(responseDto.id())
                .toUri();
        return ResponseEntity.created(location).body(responseDto);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ContractResponseDto> findById(@PathVariable Long id) {
        ContractResponseDto responseDto = contractService.findById(id);
        return ResponseEntity.ok(responseDto);
    }
}
