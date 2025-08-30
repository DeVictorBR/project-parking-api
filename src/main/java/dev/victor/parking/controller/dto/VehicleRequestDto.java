package dev.victor.parking.controller.dto;

import dev.victor.parking.entity.Vehicle;
import dev.victor.parking.entity.enums.VehicleType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record VehicleRequestDto(@NotNull Long clientId,
                                @NotBlank String licensePlate,
                                @NotNull VehicleType vehicleType) {
    public Vehicle toEntity() {
        Vehicle vehicle = new Vehicle();
        vehicle.setLicensePlate(licensePlate);
        vehicle.setVehicleType(vehicleType);
        return vehicle;
    }
}
