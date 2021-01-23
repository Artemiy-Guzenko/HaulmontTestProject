package ru.guzenko.HaulmontTestProject.backend.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Setter
@Getter
@Table(name = "bank")
public class Bank extends AbstractEntity {

    @Column(name = "bank_name")
    private String bankName;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bank")
    private List<Client> clientList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bank")
    private List<Credit> creditList;
}
