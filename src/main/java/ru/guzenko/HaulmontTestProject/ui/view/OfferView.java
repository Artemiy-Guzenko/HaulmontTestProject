package ru.guzenko.HaulmontTestProject.ui.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import ru.guzenko.HaulmontTestProject.backend.service.*;
import ru.guzenko.HaulmontTestProject.ui.view.BankView;
import ru.guzenko.HaulmontTestProject.ui.view.MainView;
import ru.guzenko.HaulmontTestProject.ui.view.layout.OfferLayout;

@Route("test")
public class OfferView extends VerticalLayout implements HasUrlParameter<String> {

    private HorizontalLayout topPanel = new HorizontalLayout();
    private VerticalLayout contentPanel;

    private final CreditOfferService creditOfferService;
    private final ClientService clientService;
    private final CreditService creditService;
    private final PaymentService paymentService;
    private final BankService bankService;

    private Long currentBankId;

    public OfferView(CreditOfferService creditOfferService, ClientService clientService,
                     CreditService creditService, PaymentService paymentService, BankService bankService) {
        this.creditOfferService = creditOfferService;
        this.clientService = clientService;
        this.creditService = creditService;
        this.paymentService = paymentService;
        this.bankService = bankService;
    }

    @Override
    public void setParameter(BeforeEvent event, String currentBankName) {
        currentBankId = bankService.findByBankName(currentBankName).getId();

        configureTopPanel(currentBankName);

        contentPanel = new OfferLayout(creditOfferService, clientService, creditService,
                paymentService, currentBankName, currentBankId);

        add(topPanel, contentPanel);
    }

    private void configureTopPanel(String currentBankName) {
        Label greeting = new Label("On this page you can see created credit offers of " + currentBankName + " bank.");
        Label info = new Label("After selecting some offer you can edit it or see payment details.");

        Button returnToBankButton = new Button("<- Return to page of " + currentBankName + " bank");
        returnToBankButton.addClickListener(click -> returnToBankButton.getUI().ifPresent(ui -> ui.navigate(BankView.class, currentBankName)));

        Button returnToMainButton = new Button("<- Return to bank choice page");
        returnToMainButton.addClickListener(click -> returnToMainButton.getUI().ifPresent(ui -> ui.navigate(MainView.class)));

        VerticalLayout leftPanel = new VerticalLayout(greeting, info);
        VerticalLayout rightPanel = new VerticalLayout(returnToBankButton, returnToMainButton);

        topPanel.add(leftPanel, rightPanel);
    }
}
