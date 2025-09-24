package dev.victor.parking.controller;

import com.github.fge.jsonpatch.JsonPatch;
import dev.victor.parking.controller.dto.VehicleRequestDto;
import dev.victor.parking.service.VehicleService;
import dev.victor.parking.service.dto.VehicleResponseDto;
import io.swagger.v3.oas.annotations.Operation;
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
@RequestMapping(path = "/vehicles")
@Tag(name = "Vehicles", description = "Management Vehicles")
public class VehicleController {

    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @PostMapping
    @Operation(summary = "Create a new Vehicle", description = "Creates a new vehicle from a JSON and returns the created vehicle")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Vehicle created successfully",
            content = @Content(schema = @Schema(implementation = VehicleResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid vehicle data provided")
    })
    public ResponseEntity<VehicleResponseDto> create(@RequestBody @Valid VehicleRequestDto dto) {
        VehicleResponseDto responseDto = vehicleService.create(dto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(responseDto.id())
                .toUri();
        return ResponseEntity.created(location).body(responseDto);
    }

    @GetMapping
    public ResponseEntity<PagedModel<VehicleResponseDto>> findAll(Pageable pageable) {
        Page<VehicleResponseDto> vehiclesPage = vehicleService.findAll(pageable);

        PagedModel.PageMetadata pageMetadata = new PagedModel.PageMetadata(
                vehiclesPage.getSize(),
                vehiclesPage.getNumber(),
                vehiclesPage.getTotalElements(),
                vehiclesPage.getTotalPages()
        );

        PagedModel<VehicleResponseDto> pagedModel = PagedModel.of(vehiclesPage.getContent(), pageMetadata);
        pagedModel.add(linkTo(methodOn(VehicleController.class).findAll(pageable)).withSelfRel());
        if (vehiclesPage.hasPrevious()) {
            pagedModel.add(linkTo(methodOn(VehicleController.class).findAll(vehiclesPage.previousPageable())).withRel("previous"));
        }
        if (vehiclesPage.hasNext()) {
            pagedModel.add(linkTo(methodOn(VehicleController.class).findAll(vehiclesPage.nextPageable())).withRel("next"));
        }
        return ResponseEntity.ok(pagedModel);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<VehicleResponseDto> findById(@PathVariable Long id) {
        VehicleResponseDto responseDto = vehicleService.findById(id);
        return ResponseEntity.ok(responseDto);
    }

    @PatchMapping(path = "/{id}", consumes = "application/json-patch+json")
    public ResponseEntity<VehicleResponseDto> updatePartial(@PathVariable Long id, @RequestBody JsonPatch patch) {
        VehicleResponseDto responseDto = vehicleService.updatePartial(id, patch);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        vehicleService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
