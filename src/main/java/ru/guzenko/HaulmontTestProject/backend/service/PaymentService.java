package ru.guzenko.HaulmontTestProject.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.guzenko.HaulmontTestProject.backend.entity.CreditOffer;
import ru.guzenko.HaulmontTestProject.backend.entity.Payment;
import ru.guzenko.HaulmontTestProject.backend.repository.PaymentRepository;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public List<Payment> findAll(Long creditOfferId) {
        return paymentRepository.findAllByOffer_Id(creditOfferId);
    }

    public void createPaymentList(CreditOffer creditOffer) {
        LocalDateTime localDateTime = LocalDateTime.now();
        Date date = Date.valueOf(localDateTime.toLocalDate());
        double scale = Math.pow(10, 2);

        double remainder = creditOffer.getCreditSum() * 0.8;
        double interest = creditOffer.getCredit().getInterestRate();
        Long period = creditOffer.getCreditPeriod() * 12;
        double paymentBody = Math.ceil(remainder / period * scale) / scale;

        for (int i = 0; i < period; i++) {
            double interestRepayment = Math.ceil(((remainder * (interest / 100)) / period) * scale) / scale;
            double paymentPerMonth = Math.ceil((paymentBody + interestRepayment) * scale) / scale;

            if (interestRepayment < 0) {
                interestRepayment = 0;
            }

            Payment newPayment = new Payment(date, paymentPerMonth, paymentBody, interestRepayment, creditOffer);
            remainder -= paymentPerMonth;
            localDateTime = localDateTime.plusMonths(1);
            date = Date.valueOf(localDateTime.toLocalDate());

            paymentRepository.save(newPayment);
        }
    }

}
