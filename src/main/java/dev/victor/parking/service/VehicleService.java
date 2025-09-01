package dev.victor.parking.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import dev.victor.parking.controller.dto.VehicleRequestDto;
import dev.victor.parking.entity.Client;
import dev.victor.parking.entity.Vehicle;
import dev.victor.parking.exception.PatchException;
import dev.victor.parking.exception.VehicleNotFoundException;
import dev.victor.parking.repository.VehicleRepository;
import dev.victor.parking.service.dto.VehicleResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class VehicleService {
    private final VehicleRepository vehicleRepository;
    private final ClientService clientService;
    private final ObjectMapper objectMapper;

    public VehicleService(VehicleRepository vehicleRepository, ClientService clientService, ObjectMapper objectMapper) {
        this.vehicleRepository = vehicleRepository;
        this.clientService = clientService;
        this.objectMapper = objectMapper;
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
        return VehicleResponseDto.toDto(vehicle);
    }

    public Page<VehicleResponseDto> findAll(Pageable pageable) {
        Page<Vehicle> vehiclesPage = vehicleRepository.findAll(pageable);
        return vehiclesPage.map(VehicleResponseDto::toDto);
    }

    public VehicleResponseDto updatePartial(Long id, JsonPatch patch) {
        try {
            Vehicle vehicle = getVehicleById(id);
            JsonNode patched = patch.apply(objectMapper.convertValue(vehicle, JsonNode.class));
            Vehicle updateVehicle = objectMapper.treeToValue(patched, Vehicle.class);
            return VehicleResponseDto.toDto(vehicleRepository.save(updateVehicle));
        } catch (JsonPatchException | JsonProcessingException e) {
            throw new PatchException("Failed to apply patch due to invalid document or malformed data");
        }
    }

    public void delete(Long id) {
        Vehicle existVehicle = getVehicleById(id);
        vehicleRepository.delete(existVehicle);
    }

    protected Vehicle getVehicleById(Long id) {
        return vehicleRepository.findById(id)
                .orElseThrow(() -> new VehicleNotFoundException(String.format("Vehicle with id: %d not found", id)));
    }
}
