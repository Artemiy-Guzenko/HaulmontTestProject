package ru.guzenko.HaulmontTestProject.backend.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Setter
@Getter
@Table
public class Payment extends AbstractEntity {

    @Column(name = "payment_date")
    private LocalDate paymentDate;

    @Column(name = "payment_amount")
    private Double paymentAmount;

    @Column(name = "payment_body")
    private Double paymentBody;

    @Column(name = "interest_repayment")
    private Double interestRepayment;

    @ManyToOne
    @JoinColumn(name = "fk_credit_offer")
    private CreditOffer offer;
}
