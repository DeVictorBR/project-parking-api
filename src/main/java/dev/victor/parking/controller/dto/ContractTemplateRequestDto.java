package dev.victor.parking.controller.dto;

import dev.victor.parking.entity.ContractTemplate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ContractTemplateRequestDto(@NotBlank String name,
                                         @NotNull BigDecimal value,
                                         @NotNull Integer dueDate) {
    public ContractTemplate toEntity() {
        ContractTemplate contractTemplate = new ContractTemplate();
        contractTemplate.setName(name);
        contractTemplate.setValue(value);
        contractTemplate.setDueDate(dueDate);
        return contractTemplate;
    }
}
