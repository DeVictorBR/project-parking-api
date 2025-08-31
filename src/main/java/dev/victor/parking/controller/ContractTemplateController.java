package dev.victor.parking.controller;

import dev.victor.parking.controller.dto.ContractTemplateRequestDto;
import dev.victor.parking.service.ContractTemplateService;
import dev.victor.parking.service.dto.ContractTemplateResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(path = "/contract-templates")
public class ContractTemplateController {

    private final ContractTemplateService contractTemplateService;

    public ContractTemplateController(ContractTemplateService contractTemplateService) {
        this.contractTemplateService = contractTemplateService;
    }

    @PostMapping
    public ResponseEntity<ContractTemplateResponseDto> create(@RequestBody ContractTemplateRequestDto dto) {
        ContractTemplateResponseDto responseDto = contractTemplateService.create(dto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(responseDto.id())
                .toUri();
        return ResponseEntity.created(location).body(responseDto);
    }
}
