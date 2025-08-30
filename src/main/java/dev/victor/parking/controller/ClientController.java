package dev.victor.parking.controller;

import com.github.fge.jsonpatch.JsonPatch;
import dev.victor.parking.controller.dto.ClientRequestDto;
import dev.victor.parking.service.ClientService;
import dev.victor.parking.service.dto.ClientHateoasResponse;
import dev.victor.parking.service.dto.ClientResponseDto;
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
@RequestMapping(path = "/clients")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping
    public ResponseEntity<ClientHateoasResponse> create(@RequestBody @Valid ClientRequestDto dto) {
        ClientResponseDto responseDto = clientService.create(dto);
        ClientHateoasResponse hateoasResponse = new ClientHateoasResponse(responseDto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(responseDto.clientId())
                .toUri();
        hateoasResponse.add(linkTo(methodOn(VehicleController.class).create(null)).withRel("add-vehicle"));
        return ResponseEntity.created(location).body(hateoasResponse);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ClientHateoasResponse> findById(@PathVariable Long id) {
        ClientResponseDto responseDto = clientService.findById(id);
        ClientHateoasResponse hateoasResponse = new ClientHateoasResponse(responseDto);
        hateoasResponse.add(linkTo(methodOn(ClientController.class).findById(id)).withSelfRel());

        hateoasResponse.add(linkTo(methodOn(ClientController.class).updatePartial(id, null)).withRel("update"));
        hateoasResponse.add(linkTo(methodOn(ClientController.class).delete(id)).withRel("delete"));
        return ResponseEntity.ok(hateoasResponse);
    }

    @GetMapping
    public ResponseEntity<PagedModel<ClientResponseDto>> findAll(Pageable pageable) {
        Page<ClientResponseDto> clientsPage = clientService.findAll(pageable);

        PagedModel.PageMetadata pageMetadata = new PagedModel.PageMetadata(
                clientsPage.getSize(),
                clientsPage.getNumber(),
                clientsPage.getTotalElements(),
                clientsPage.getTotalPages()
        );

        PagedModel<ClientResponseDto> pagedModel = PagedModel.of(clientsPage.getContent(), pageMetadata);

        pagedModel.add(linkTo(methodOn(ClientController.class).findAll(pageable)).withSelfRel());
        if (clientsPage.hasPrevious()) {
            pagedModel.add(linkTo(methodOn(ClientController.class).findAll(clientsPage.previousPageable())).withRel("previous"));
        }
        if (clientsPage.hasNext()) {
            pagedModel.add(linkTo(methodOn(ClientController.class).findAll(clientsPage.nextPageable())).withRel("next"));
        }
        return ResponseEntity.ok(pagedModel);
    }

    @PatchMapping(path = "/{id}", consumes = "application/json-patch+json")
    public ResponseEntity<ClientResponseDto> updatePartial(@PathVariable Long id, @RequestBody JsonPatch patch) {
        ClientResponseDto responseDto = clientService.updatePartial(id, patch);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        clientService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
