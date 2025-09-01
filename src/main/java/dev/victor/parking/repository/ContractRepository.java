package dev.victor.parking.repository;

import dev.victor.parking.entity.Contract;
import dev.victor.parking.entity.enums.ContractStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface ContractRepository extends JpaRepository<Contract, Long> {
    Page<Contract> findByContractStatusAndRenewalDateBetween(
            ContractStatus status, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable
    );
}
