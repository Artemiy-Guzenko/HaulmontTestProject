package ru.guzenko.HaulmontTestProject.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.guzenko.HaulmontTestProject.backend.entity.Client;
import ru.guzenko.HaulmontTestProject.backend.entity.Credit;
import ru.guzenko.HaulmontTestProject.backend.repository.CreditRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CreditService {

    private final CreditRepository creditRepository;

    public List<Credit> findAll(Long bankId) {
        return creditRepository.findAllByBank_Id(bankId);
    }

    public void delete(Credit credit) {
        creditRepository.delete(credit);
    }

    public void save(Credit credit) {
        creditRepository.save(credit);
    }
}
