package ru.guzenko.HaulmontTestProject.backend.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@MappedSuperclass
@Setter
@Getter
public abstract class AbstractEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue
    private Long id;
}
