package dev.victor.parking.controller;

import com.github.fge.jsonpatch.JsonPatch;
import dev.victor.parking.controller.dto.ClientRequestDto;
import dev.victor.parking.service.ClientService;
import dev.victor.parking.service.dto.ClientHateoasResponse;
import dev.victor.parking.service.dto.ClientResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Clients", description = "Management Clients")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping
    @Operation(summary = "Create a new Client", description = "Creates a new client from a JSON and returns the created client")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Client created successfully",
            content = @Content(schema = @Schema(implementation = ClientHateoasResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid client data provided")
    })
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
    @Operation(summary = "Find Client by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Client found and returned successfully",
                    content = @Content(schema = @Schema(implementation = ClientHateoasResponse.class))),
            @ApiResponse(responseCode = "404", description = "Client not found")
    })
    public ResponseEntity<ClientHateoasResponse> findById(@PathVariable @Parameter(description = "Client ID to fetch", example = "1") Long id) {
        ClientResponseDto responseDto = clientService.findById(id);
        ClientHateoasResponse hateoasResponse = new ClientHateoasResponse(responseDto);
        hateoasResponse.add(linkTo(methodOn(ClientController.class).findById(id)).withSelfRel());

        hateoasResponse.add(linkTo(methodOn(ClientController.class).updatePartial(id, null)).withRel("update"));
        hateoasResponse.add(linkTo(methodOn(ClientController.class).delete(id)).withRel("delete"));
        return ResponseEntity.ok(hateoasResponse);
    }

    @GetMapping
    @Operation(summary = "Find All Clients", description = "Search all Clients and respond with pagination")
    @ApiResponse(responseCode = "200", description = "List of clients found and returned successfully")
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
    @Operation(summary = "Update a Client", description = "Updates one or more fields of a Client using the JSON Patch format")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Client updated successfully",
                    content = @Content(schema = @Schema(implementation = ClientResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid JSON Patch document or data"),
            @ApiResponse(responseCode = "404", description = "Client not found")
    })
    public ResponseEntity<ClientResponseDto> updatePartial(@PathVariable @Parameter(description = "Client ID for updated", example = "1") Long id, @RequestBody JsonPatch patch) {
        ClientResponseDto responseDto = clientService.updatePartial(id, patch);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping(path = "/{id}")
    @Operation(summary = "Delete a Client", description = "Deletes a client by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Client deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Client not found")
    })
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        clientService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
