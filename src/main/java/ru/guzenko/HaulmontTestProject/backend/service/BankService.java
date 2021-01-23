package ru.guzenko.HaulmontTestProject.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.guzenko.HaulmontTestProject.backend.entity.Bank;
import ru.guzenko.HaulmontTestProject.backend.repository.BankRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BankService {

    private final BankRepository bankRepository;


    public List<Bank> findAll() {
        return bankRepository.findAll();
    }

    public void delete(Bank bank) {
        bankRepository.delete(bank);
    }

    public void save(Bank bank) {
        bankRepository.save(bank);
    }

    public Bank findByBankName(String bankName) {
        return bankRepository.findByBankName(bankName);
    }
}
