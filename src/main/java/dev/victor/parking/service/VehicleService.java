package dev.victor.parking.service;

import dev.victor.parking.controller.dto.VehicleRequestDto;
import dev.victor.parking.entity.Client;
import dev.victor.parking.entity.Vehicle;
import dev.victor.parking.exception.VehicleNotFoundException;
import dev.victor.parking.repository.VehicleRepository;
import dev.victor.parking.service.dto.VehicleResponseDto;
import org.springframework.stereotype.Service;

@Service
public class VehicleService {
    private final VehicleRepository vehicleRepository;
    private final ClientService clientService;

    public VehicleService(VehicleRepository vehicleRepository, ClientService clientService) {
        this.vehicleRepository = vehicleRepository;
        this.clientService = clientService;
    }

    public VehicleResponseDto create(VehicleRequestDto dto) {
        Client client = clientService.getClientById(dto.clientId());
        Vehicle vehicle = dto.toEntity();
        vehicle.setClient(client);
        Vehicle savedVehicle = vehicleRepository.save(vehicle);
        return VehicleResponseDto.toDto(savedVehicle);
    }

    public VehicleResponseDto findById(Long id) {
        Vehicle vehicle = getVehicleById(id);
        Vehicle savedVehicle = vehicleRepository.save(vehicle);
        return VehicleResponseDto.toDto(savedVehicle);
    }

    protected Vehicle getVehicleById(Long id) {
        return vehicleRepository.findById(id)
                .orElseThrow(() -> new VehicleNotFoundException(String.format("Vehicle with id: %d not found", id)));
    }
}
