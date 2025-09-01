package dev.victor.parking.entity;

import dev.victor.parking.entity.enums.ContractStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "tb_contracts")
public class Contract {

    @Id
    @Column(name = "contract_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "contract")
    private Vehicle vehicle;

    @ManyToOne
    @JoinColumn(name = "contract_template_id")
    private ContractTemplate contractTemplate;

    @OneToOne
    @JoinColumn(name = "last_payment_id")
    private Payment lastPayment;

    @Enumerated(EnumType.STRING)
    private ContractStatus contractStatus;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "renewal_date")
    private LocalDateTime renewalDate;

    @OneToMany(mappedBy = "contract", cascade = CascadeType.ALL)
    private List<Payment> payments;

    public Contract() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public ContractTemplate getContractTemplate() {
        return contractTemplate;
    }

    public void setContractTemplate(ContractTemplate contractTemplate) {
        this.contractTemplate = contractTemplate;
    }

    public Payment getLastPayment() {
        return lastPayment;
    }

    public void setLastPayment(Payment lastPayment) {
        this.lastPayment = lastPayment;
    }

    public ContractStatus getContractStatus() {
        return contractStatus;
    }

    public void setContractStatus(ContractStatus contractStatus) {
        this.contractStatus = contractStatus;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getRenewalDate() {
        return renewalDate;
    }

    public void setRenewalDate(LocalDateTime renewalDate) {
        this.renewalDate = renewalDate;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }
}
