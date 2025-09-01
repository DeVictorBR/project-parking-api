package dev.victor.parking.service;

import dev.victor.parking.controller.dto.ContractRequestDto;
import dev.victor.parking.entity.Contract;
import dev.victor.parking.entity.ContractTemplate;
import dev.victor.parking.entity.Vehicle;
import dev.victor.parking.entity.enums.ContractStatus;
import dev.victor.parking.exception.ContractAlreadyExistsException;
import dev.victor.parking.repository.ContractRepository;
import dev.victor.parking.service.dto.ContractResponseDto;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ContractService {

    private final ContractRepository contractRepository;
    private final ContractTemplateService contractTemplateService;
    private final VehicleService vehicleService;

    public ContractService(ContractRepository contractRepository, ContractTemplateService contractTemplateService, VehicleService vehicleService) {
        this.contractRepository = contractRepository;
        this.contractTemplateService = contractTemplateService;
        this.vehicleService = vehicleService;
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


}
