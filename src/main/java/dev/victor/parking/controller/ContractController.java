package dev.victor.parking.controller;

import dev.victor.parking.controller.dto.ContractRequestDto;
import dev.victor.parking.service.ContractService;
import dev.victor.parking.service.dto.ContractResponseDto;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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

    @GetMapping
    public ResponseEntity<PagedModel<ContractResponseDto>> findAll(Pageable pageable) {
        Page<ContractResponseDto> contractsPage = contractService.findAll(pageable);

        PagedModel.PageMetadata pageMetadata = new PagedModel.PageMetadata(
                contractsPage.getSize(),
                contractsPage.getNumber(),
                contractsPage.getTotalElements(),
                contractsPage.getTotalPages()
        );

        PagedModel<ContractResponseDto> pagedModel = PagedModel.of(contractsPage.getContent(), pageMetadata);

        pagedModel.add(linkTo(methodOn(ContractController.class).findAll(pageable)).withSelfRel());
        if(contractsPage.hasPrevious()) {
            pagedModel.add(linkTo(methodOn(ContractController.class).findAll(contractsPage.previousPageable())).withRel("previous"));
        }
        if (contractsPage.hasNext()) {
            pagedModel.add(linkTo(methodOn(ContractController.class).findAll(contractsPage.nextPageable())).withRel("next"));
        }
        return ResponseEntity.ok(pagedModel);
    }
}
