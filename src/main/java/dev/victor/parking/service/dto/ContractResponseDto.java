package dev.victor.parking.service.dto;

import dev.victor.parking.entity.Contract;
import dev.victor.parking.entity.enums.ContractStatus;

import java.time.LocalDateTime;

public record ContractResponseDto(Long id,
                                  Long vehicleId,
                                  Long contractTemplateId,
                                  Long lastPaymentId,
                                  ContractStatus status,
                                  LocalDateTime startDate,
                                  LocalDateTime renewalDate) {
    public static ContractResponseDto toDto(Contract contract) {
        return new ContractResponseDto(
                contract.getId(),
                contract.getVehicle().getId(),
                contract.getContractTemplate().getId(),
                contract.getLastPayment() != null ? contract.getLastPayment().getId() : null,
                contract.getContractStatus(),
                contract.getStartDate(),
                contract.getRenewalDate()
        );
    }
}
