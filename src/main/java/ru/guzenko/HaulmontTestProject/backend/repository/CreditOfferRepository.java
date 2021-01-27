package ru.guzenko.HaulmontTestProject.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.guzenko.HaulmontTestProject.backend.entity.CreditOffer;

import java.util.List;

public interface CreditOfferRepository extends JpaRepository<CreditOffer, Long> {

    List<CreditOffer> findAllByClient_Bank_Id(Long bankId);
}
