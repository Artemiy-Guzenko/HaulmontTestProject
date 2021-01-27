package ru.guzenko.HaulmontTestProject.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.guzenko.HaulmontTestProject.backend.entity.CreditOffer;
import ru.guzenko.HaulmontTestProject.backend.repository.CreditOfferRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CreditOfferService {

    private final CreditOfferRepository offerRepository;

    public List<CreditOffer> findAll(Long bankId) {
        return offerRepository.findAllByClient_Bank_Id(bankId);
    }

    public List<CreditOffer> findAll() {
        return offerRepository.findAll();
    }

    public void delete(CreditOffer creditOffer) {
        offerRepository.delete(creditOffer);
    }

    public void save (CreditOffer creditOffer) {
        offerRepository.save(creditOffer);
    }

    public Optional<CreditOffer> findById(Long id) {
        return offerRepository.findById(id);
    }
}
