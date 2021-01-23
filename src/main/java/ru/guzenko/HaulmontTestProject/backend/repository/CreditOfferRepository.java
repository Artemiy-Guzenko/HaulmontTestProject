package ru.guzenko.HaulmontTestProject.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.guzenko.HaulmontTestProject.backend.entity.CreditOffer;

public interface CreditOfferRepository extends JpaRepository<CreditOffer, Long> {
}
