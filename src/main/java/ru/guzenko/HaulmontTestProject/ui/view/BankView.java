package ru.guzenko.HaulmontTestProject.ui.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import ru.guzenko.HaulmontTestProject.backend.service.BankService;
import ru.guzenko.HaulmontTestProject.backend.service.ClientService;
import ru.guzenko.HaulmontTestProject.backend.service.CreditService;
import ru.guzenko.HaulmontTestProject.ui.view.layout.ClientLayout;
import ru.guzenko.HaulmontTestProject.ui.view.layout.CreditLayout;

@Route("bank")
public class BankView extends Div implements HasUrlParameter<String> {

    private final HorizontalLayout topPanel = new HorizontalLayout();
    private final HorizontalLayout contentPanel = new HorizontalLayout();

    private final ClientService clientService;
    private final CreditService creditService;
    private final BankService bankService;

    private Long currentBankId;
    private String currentBankName;

    public BankView(ClientService clientService, CreditService creditService, BankService bankService) {
        this.clientService = clientService;
        this.creditService = creditService;
        this.bankService = bankService;
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, String parameter) {
        currentBankName = parameter;
        currentBankId = bankService.findByBankName(currentBankName).getId();

        addClassName("current-bank-view");

        configureTopPanel();
        configureContentPanel();

        add(topPanel, contentPanel);
    }

    private void configureContentPanel() {
        VerticalLayout clientLayout = new ClientLayout(clientService, bankService, currentBankName);
        VerticalLayout creditLayout = new CreditLayout(creditService, bankService, currentBankId);

        contentPanel.addAndExpand(clientLayout, creditLayout);
    }

    private void configureTopPanel() {
        Label greeting = new Label("Welcome on page of " + currentBankName + " bank!");
        Label info = new Label("In tables below you can see information about clients and credits of this bank");
        Label info2 = new Label("If you want to edit or delete some information just click on one of lines");

        Button returnButton = new Button("<- Return to bank list");
        returnButton.addClickListener(click -> returnButton.getUI().ifPresent(ui -> ui.navigate(MainView.class)));

        Button goToOfferButton = new Button("Go to credit offers ->");
        goToOfferButton.addClickListener(click -> goToOfferButton.getUI().ifPresent(ui -> ui.navigate(OfferView.class, currentBankName)));

        VerticalLayout leftPanel = new VerticalLayout();
        VerticalLayout rightPanel = new VerticalLayout();

        leftPanel.add(greeting, info, info2);
        rightPanel.add(returnButton, goToOfferButton);

        topPanel.add(leftPanel, rightPanel);

    }
}
