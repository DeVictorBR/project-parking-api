package dev.victor.parking.service;

import dev.victor.parking.controller.dto.ContractTemplateRequestDto;
import dev.victor.parking.entity.ContractTemplate;
import dev.victor.parking.exception.ContractTemplateNotFoundException;
import dev.victor.parking.repository.ContractTemplateRepository;
import dev.victor.parking.service.dto.ContractTemplateResponseDto;
import org.springframework.stereotype.Service;

@Service
public class ContractTemplateService {

    private final ContractTemplateRepository contractTemplateRepository;

    public ContractTemplateService(ContractTemplateRepository contractTemplateRepository) {
        this.contractTemplateRepository = contractTemplateRepository;
    }

    public ContractTemplateResponseDto create(ContractTemplateRequestDto dto) {
        ContractTemplate contractTemplate = dto.toEntity();
        ContractTemplate savedContractTemplate = contractTemplateRepository.save(contractTemplate);
        return ContractTemplateResponseDto.toDto(savedContractTemplate);
    }

    public ContractTemplateResponseDto findById(Long id) {
        ContractTemplate contractTemplate = getContractTemplateById(id);
        return ContractTemplateResponseDto.toDto(contractTemplate);
    }

    protected ContractTemplate getContractTemplateById(Long id) {
        return contractTemplateRepository.findById(id)
                .orElseThrow(() -> new ContractTemplateNotFoundException(String.format("Contract Template with id: %d not found", id)));
    }
}
