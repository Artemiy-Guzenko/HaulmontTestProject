package ru.guzenko.HaulmontTestProject.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.guzenko.HaulmontTestProject.backend.entity.Bank;

public interface BankRepository extends JpaRepository<Bank, Long> {

    Bank findByBankName(String bankName);
}
