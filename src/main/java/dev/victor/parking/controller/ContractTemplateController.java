package dev.victor.parking.controller;

import dev.victor.parking.controller.dto.ContractTemplateRequestDto;
import dev.victor.parking.service.ContractTemplateService;
import dev.victor.parking.service.dto.ContractTemplateResponseDto;
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

    @GetMapping(path = "/{id}")
    public ResponseEntity<ContractTemplateResponseDto> findById(@PathVariable Long id) {
        ContractTemplateResponseDto responseDto = contractTemplateService.findById(id);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping
    public ResponseEntity<PagedModel<ContractTemplateResponseDto>> findAll(Pageable pageable) {
        Page<ContractTemplateResponseDto> contractTemplatesPage = contractTemplateService.findAll(pageable);

        PagedModel.PageMetadata pageMetadata = new PagedModel.PageMetadata(
                contractTemplatesPage.getSize(),
                contractTemplatesPage.getNumber(),
                contractTemplatesPage.getTotalElements(),
                contractTemplatesPage.getTotalPages()
        );

        PagedModel<ContractTemplateResponseDto> pagedModel = PagedModel.of(contractTemplatesPage.getContent(), pageMetadata);
        pagedModel.add(linkTo(methodOn(ContractTemplateController.class).findAll(pageable)).withSelfRel());
        if (contractTemplatesPage.hasPrevious()) {
            pagedModel.add(linkTo(methodOn(ContractTemplateController.class).findAll(contractTemplatesPage.previousPageable())).withRel("previous"));
        }
        if (contractTemplatesPage.hasNext()) {
            pagedModel.add(linkTo(methodOn(ContractTemplateController.class).findAll(contractTemplatesPage.nextPageable())).withRel("next"));
        }
        return ResponseEntity.ok(pagedModel);
    }
}
