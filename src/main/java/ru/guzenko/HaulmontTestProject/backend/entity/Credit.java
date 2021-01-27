package ru.guzenko.HaulmontTestProject.backend.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Setter
@Getter
@Table(name = "credit")
public class Credit extends AbstractEntity {

    @Column(name = "credit_limit")
    private Double creditLimit;

    @Column(name = "interest_rate")
    private Double interestRate;

    @OneToMany(mappedBy = "credit")
    private List<CreditOffer> creditOffers;

    @ManyToOne
    @JoinColumn(name = "fk_credit_bank")
    private Bank bank;

    @Override
    public String toString() {
        return "up to " + this.creditLimit + " $, " + this.interestRate + " %";
    }
}
