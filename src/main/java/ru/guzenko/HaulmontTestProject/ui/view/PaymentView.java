package ru.guzenko.HaulmontTestProject.ui.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import ru.guzenko.HaulmontTestProject.backend.entity.*;
import ru.guzenko.HaulmontTestProject.backend.service.CreditOfferService;
import ru.guzenko.HaulmontTestProject.backend.service.PaymentService;

@Route("payment")
public class PaymentView extends VerticalLayout implements HasUrlParameter<Long> {

    private final PaymentService paymentService;
    private final CreditOfferService creditOfferService;
    private String currentBankName;

    private final Grid<Payment> paymentGrid = new Grid<>(Payment.class);

    public PaymentView(PaymentService paymentService, CreditOfferService creditOfferService) {
        this.paymentService = paymentService;
        this.creditOfferService = creditOfferService;
    }

    @Override
    public void setParameter(BeforeEvent event, Long currentOfferId) {
        CreditOffer currentOffer = creditOfferService.findById(currentOfferId).get();
        Client currentClient = currentOffer.getClient();

        Bank currentBank = currentClient.getBank();
        currentBankName = currentBank.getBankName();

        Credit currentCredit = currentOffer.getCredit();
        Long currentCreditSum = currentOffer.getCreditSum();

        Label offerInfo = new Label("Details for offer of client " + currentClient + " by credit "
                + currentCredit + " for " + currentCreditSum + " $");

        paymentGrid.setColumns("paymentDate", "paymentAmount", "paymentBody", "interestRepayment");
        paymentGrid.setWidthFull();
        paymentGrid.setHeight("500px");
        paymentGrid.setItems(paymentService.findAll(currentOfferId));

        add(offerInfo ,getToolBar(currentBankName), paymentGrid);
    }

    private HorizontalLayout getToolBar(String currentBankName) {
        Button returnToBankButton = new Button("<- Return to page of " + currentBankName + " bank");
        Button returnToMainButton = new Button("<- Return to bank choice page");
        Button returnToOffersButton = new Button(("<- Return to page of offers of " + currentBankName + " bank"));

        returnToBankButton.addClickListener(click -> returnToBankButton
                .getUI().ifPresent(ui -> ui.navigate(BankView.class, currentBankName)));
        returnToMainButton.addClickListener(click -> returnToMainButton
                .getUI().ifPresent(ui -> ui.navigate(MainView.class)));
        returnToOffersButton.addClickListener((click -> returnToOffersButton
                .getUI().ifPresent(ui -> ui.navigate(OfferView.class, currentBankName))));

        HorizontalLayout toolBar = new HorizontalLayout(returnToOffersButton, returnToBankButton, returnToMainButton);

        return toolBar;
    }
}
