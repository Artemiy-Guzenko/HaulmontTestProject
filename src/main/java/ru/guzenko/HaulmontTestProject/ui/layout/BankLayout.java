package ru.guzenko.HaulmontTestProject.ui.layout;

import com.helger.commons.url.URLParameter;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import ru.guzenko.HaulmontTestProject.backend.entity.Bank;
import ru.guzenko.HaulmontTestProject.backend.entity.Client;
import ru.guzenko.HaulmontTestProject.backend.entity.Credit;
import ru.guzenko.HaulmontTestProject.backend.service.BankService;
import ru.guzenko.HaulmontTestProject.backend.service.ClientService;
import ru.guzenko.HaulmontTestProject.backend.service.CreditService;
import ru.guzenko.HaulmontTestProject.ui.MainLayout;
import ru.guzenko.HaulmontTestProject.ui.form.ClientForm;
import ru.guzenko.HaulmontTestProject.ui.form.CreditForm;

import javax.annotation.PostConstruct;
import java.util.List;

@Route("bank")
public class BankLayout extends Div implements HasUrlParameter<String> {

    private ClientForm clientForm;
    private CreditForm creditForm;
    private Grid<Client> clientGrid = new Grid<>(Client.class);
    private Grid<Credit> creditGrid = new Grid<>(Credit.class);
    private HorizontalLayout contentLayout = new HorizontalLayout();
    private VerticalLayout labelLayout = new VerticalLayout();

    private final ClientService clientService;
    private final CreditService creditService;

    private String currentBankName;

    public BankLayout(ClientService clientService, CreditService creditService) {
        this.clientService = clientService;
        this.creditService = creditService;
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, String parameter) {
        currentBankName = parameter;

        addClassName("current-bank-view");

        VerticalLayout clientLayout = new ClientLayout(clientService, currentBankName);
        VerticalLayout creditLayout = new CreditLayout(creditService);

        configureLabels();
        add(labelLayout);

        contentLayout.addAndExpand(clientLayout, creditLayout);
        add(contentLayout);
    }


    private void configureLabels() {

        Label greeting = new Label("Welcome on page of " + currentBankName + " bank!");
        Label info = new Label("In tables below you can see information about clients and credits of this bank");
        Label info2 = new Label("If you want to edit or delete some information just click on one of lines");

        Button returnButton = new Button("<- Return to bank list");
        returnButton.addClickListener(click -> returnButton.getUI().ifPresent(ui -> ui.navigate(MainLayout.class)));

        labelLayout.add(greeting, info, info2, returnButton);
    }
}
