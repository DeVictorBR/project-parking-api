package dev.victor.parking.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import dev.victor.parking.controller.dto.ContractRequestDto;
import dev.victor.parking.entity.Contract;
import dev.victor.parking.entity.ContractTemplate;
import dev.victor.parking.entity.Vehicle;
import dev.victor.parking.entity.enums.ContractStatus;
import dev.victor.parking.exception.ContractAlreadyExistsException;
import dev.victor.parking.exception.ContractNotFoundException;
import dev.victor.parking.exception.PatchException;
import dev.victor.parking.repository.ContractRepository;
import dev.victor.parking.service.dto.ContractResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ContractService {

    private final ContractRepository contractRepository;
    private final ContractTemplateService contractTemplateService;
    private final VehicleService vehicleService;
    private final ObjectMapper objectMapper;

    public ContractService(ContractRepository contractRepository, ContractTemplateService contractTemplateService, VehicleService vehicleService, ObjectMapper objectMapper) {
        this.contractRepository = contractRepository;
        this.contractTemplateService = contractTemplateService;
        this.vehicleService = vehicleService;
        this.objectMapper = objectMapper;
    }

    public ContractResponseDto create(ContractRequestDto dto) {
        Vehicle vehicle = vehicleService.getVehicleById(dto.vehicleId());
        if (vehicle.getContract() != null) {
            throw new ContractAlreadyExistsException(String.format("Vehicle with license plate '%s' already has an active contract", vehicle.getLicensePlate()));
        }
        ContractTemplate contractTemplate = contractTemplateService.getContractTemplateById(dto.contractTemplateId());
        Contract contract = dto.toEntity();
        contract.setVehicle(vehicle);
        contract.setContractTemplate(contractTemplate);
        contract.setContractStatus(ContractStatus.ACTIVE);
        contract.setStartDate(dto.startDate());

        int dueDate = contractTemplate.getDueDate();
        LocalDateTime renewalDate = dto.startDate().withDayOfMonth(dueDate);

        if (renewalDate.isBefore(dto.startDate())) {
            renewalDate = renewalDate.plusMonths(1);
        }
        contract.setRenewalDate(renewalDate);

        Contract savedContract = contractRepository.save(contract);
        vehicle.setContract(savedContract);
        vehicleService.save(vehicle);
        return ContractResponseDto.toDto(contract);
    }

    public ContractResponseDto findById(Long id) {
        Contract contract = getContractById(id);
        return ContractResponseDto.toDto(contract);
    }

    public Page<ContractResponseDto> findAll(Pageable pageable) {
        Page<Contract> contractsPage = contractRepository.findAll(pageable);
        return contractsPage.map(ContractResponseDto::toDto);
    }

    public ContractResponseDto updatePartial(Long id, JsonPatch patch) {
        try {
            Contract contract = getContractById(id);
            JsonNode patched = patch.apply(objectMapper.convertValue(contract, JsonNode.class));
            Contract updatedContract = objectMapper.treeToValue(patched, Contract.class);
            return ContractResponseDto.toDto(contractRepository.save(updatedContract));
        } catch (JsonPatchException | JsonProcessingException e) {
            throw new PatchException("Failed to apply patch due to invalid document or malformed data");
        }
    }

    protected Contract getContractById(Long id) {
        return contractRepository.findById(id)
                .orElseThrow(() -> new ContractNotFoundException(String.format("Contract with id: %d not found", id)));
    }
}
