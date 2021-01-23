package ru.guzenko.HaulmontTestProject.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.guzenko.HaulmontTestProject.backend.entity.Credit;

import java.util.List;

public interface CreditRepository extends JpaRepository<Credit, Long> {

    List<Credit> findAllByBank_Id(Long bankId);
}
