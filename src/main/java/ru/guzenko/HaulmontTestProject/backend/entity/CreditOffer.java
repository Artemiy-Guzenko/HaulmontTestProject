package ru.guzenko.HaulmontTestProject.backend.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Setter
@Getter
@Table(name = "credit_offer")
public class CreditOffer extends AbstractEntity {

    @OneToOne
    @JoinColumn(name = "fk_client", referencedColumnName = "id")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "fk_credit")
    private Credit credit;

    @Column(name = "credit_sum")
    private Double creditSum;

    @OneToMany(mappedBy = "offer", cascade = CascadeType.ALL)
    private List<Payment> paymentSchedule;

}
