package ru.guzenko.HaulmontTestProject.ui.view;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import ru.guzenko.HaulmontTestProject.backend.entity.Client;
import ru.guzenko.HaulmontTestProject.backend.entity.Credit;
import ru.guzenko.HaulmontTestProject.backend.entity.CreditOffer;
import ru.guzenko.HaulmontTestProject.backend.entity.Payment;
import ru.guzenko.HaulmontTestProject.backend.service.CreditOfferService;
import ru.guzenko.HaulmontTestProject.backend.service.PaymentService;

@Route("payment")
public class PaymentView extends VerticalLayout implements HasUrlParameter<Long> {

    private final PaymentService paymentService;
    private final CreditOfferService creditOfferService;

    private Grid<Payment> paymentGrid = new Grid<>(Payment.class);

    public PaymentView(PaymentService paymentService, CreditOfferService creditOfferService) {
        this.paymentService = paymentService;
        this.creditOfferService = creditOfferService;
    }

    @Override
    public void setParameter(BeforeEvent event, Long currentOfferId) {
        CreditOffer currentOffer = creditOfferService.findById(currentOfferId).get();
        Client currentClient = currentOffer.getClient();
        Credit currentCredit = currentOffer.getCredit();
        Long currentCreditSum = currentOffer.getCreditSum();

        Label offferInfo = new Label("Details for offer of client " + currentClient + " by credit "
                + currentCredit + " for " + currentCreditSum + " $");

        paymentGrid.setColumns("paymentDate", "paymentAmount", "paymentBody", "interestRepayment");
        paymentGrid.setSizeFull();
        paymentGrid.setItems(paymentService.findAll(currentOfferId));

        add(paymentGrid);
    }
}
