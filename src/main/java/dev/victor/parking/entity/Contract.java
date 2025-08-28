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

    @OneToOne
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
}
