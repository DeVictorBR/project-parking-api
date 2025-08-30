package dev.victor.parking.service.dto;

import dev.victor.parking.entity.Vehicle;
import dev.victor.parking.entity.enums.VehicleType;

public record VehicleResponseDto(Long id,
                                 Long clientId,
                                 String licensePlate,
                                 VehicleType vehicleType) {
    public static VehicleResponseDto toDto(Vehicle vehicle) {
        return new VehicleResponseDto(vehicle.getId(),
                vehicle.getClient().getId(),
                vehicle.getLicensePlate(),
                vehicle.getVehicleType()
        );
    }
}
