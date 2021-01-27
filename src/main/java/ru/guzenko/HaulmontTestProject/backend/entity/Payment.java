package ru.guzenko.HaulmontTestProject.backend.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Setter
@Getter
@Table(name = "payment")
public class Payment extends AbstractEntity {

    @Column(name = "payment_date")
    private Date paymentDate;

    @Column(name = "payment_amount")
    private Double paymentAmount;

    @Column(name = "payment_body")
    private Double paymentBody;

    @Column(name = "interest_repayment")
    private Double interestRepayment;

    @ManyToOne
    @JoinColumn(name = "fk_credit_offer")
    private CreditOffer offer;

    public Payment(Date paymentDate, Double paymentAmount, Double paymentBody, Double interestRepayment, CreditOffer offer) {
        this.paymentDate = paymentDate;
        this.paymentAmount = paymentAmount;
        this.paymentBody = paymentBody;
        this.interestRepayment = interestRepayment;
        this.offer = offer;
    }

    public Payment() {

    }
}
