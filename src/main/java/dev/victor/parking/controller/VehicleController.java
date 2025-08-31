package dev.victor.parking.controller;

import dev.victor.parking.controller.dto.VehicleRequestDto;
import dev.victor.parking.service.VehicleService;
import dev.victor.parking.service.dto.VehicleResponseDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

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

    @GetMapping(path = "/{id}")
    public ResponseEntity<VehicleResponseDto> findById(@PathVariable Long id) {
        VehicleResponseDto responseDto = vehicleService.findById(id);
        return ResponseEntity.ok(responseDto);
    }
}
