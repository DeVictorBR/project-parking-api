package dev.victor.parking.service.dto;

import dev.victor.parking.entity.ContractTemplate;

import java.math.BigDecimal;

public record ContractTemplateResponseDto(Long id,
                                          String name,
                                          BigDecimal value,
                                          Integer dueDate) {
    public static ContractTemplateResponseDto toDto(ContractTemplate contractTemplate) {
        return new ContractTemplateResponseDto(
                contractTemplate.getId(),
                contractTemplate.getName(),
                contractTemplate.getValue(),
                contractTemplate.getDueDate()
        );
    }
}
