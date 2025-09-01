package dev.victor.parking.controller.dto;

import dev.victor.parking.entity.Contract;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record ContractRequestDto(@NotNull Long vehicleId,
                                 @NotNull Long contractTemplateId,
                                 @NotNull LocalDateTime startDate){
    public Contract toEntity() {
        return new Contract();
    }
}
