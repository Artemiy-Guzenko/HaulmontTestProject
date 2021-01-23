package ru.guzenko.HaulmontTestProject.backend.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Setter
@Getter
@Table
@RequiredArgsConstructor
public class Client extends AbstractEntity {

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "email")
    private String email;

    @Column(name = "passport")
    private String passport;

    @OneToOne(mappedBy = "client")
    private CreditOffer creditOffer;

    @ManyToOne
    @JoinColumn(name = "fk_client_bank")
    private Bank bank;
}
