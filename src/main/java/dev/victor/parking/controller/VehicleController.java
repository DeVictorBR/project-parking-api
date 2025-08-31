package dev.victor.parking.controller;

import dev.victor.parking.controller.dto.VehicleRequestDto;
import dev.victor.parking.service.VehicleService;
import dev.victor.parking.service.dto.VehicleResponseDto;
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
public class VehicleController {

    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @PostMapping
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
}
