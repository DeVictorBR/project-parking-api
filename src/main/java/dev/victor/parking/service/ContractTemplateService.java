package dev.victor.parking.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import dev.victor.parking.controller.dto.ContractTemplateRequestDto;
import dev.victor.parking.entity.ContractTemplate;
import dev.victor.parking.exception.ContractTemplateNotFoundException;
import dev.victor.parking.exception.PatchException;
import dev.victor.parking.repository.ContractTemplateRepository;
import dev.victor.parking.service.dto.ContractTemplateResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ContractTemplateService {

    private final ContractTemplateRepository contractTemplateRepository;
    private final ObjectMapper objectMapper;

    public ContractTemplateService(ContractTemplateRepository contractTemplateRepository, ObjectMapper objectMapper) {
        this.contractTemplateRepository = contractTemplateRepository;
        this.objectMapper = objectMapper;
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

    public Page<ContractTemplateResponseDto> findAll(Pageable pageable) {
        Page<ContractTemplate> contractTemplatesPage = contractTemplateRepository.findAll(pageable);
        return contractTemplatesPage.map(ContractTemplateResponseDto::toDto);
    }

    public ContractTemplateResponseDto updatePartial(Long id, JsonPatch patch) {
        try {
            ContractTemplate contractTemplate = getContractTemplateById(id);
            JsonNode patched = patch.apply(objectMapper.convertValue(contractTemplate, JsonNode.class));
            ContractTemplate updateContractTemplate = objectMapper.treeToValue(patched, ContractTemplate.class);
            return ContractTemplateResponseDto.toDto(updateContractTemplate);
        } catch (JsonPatchException | JsonProcessingException e) {
            throw new PatchException("Failed to apply patch due to invalid document or malformed data");
        }
    }

    protected ContractTemplate getContractTemplateById(Long id) {
        return contractTemplateRepository.findById(id)
                .orElseThrow(() -> new ContractTemplateNotFoundException(String.format("Contract Template with id: %d not found", id)));
    }
}
