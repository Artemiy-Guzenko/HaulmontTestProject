package ru.guzenko.HaulmontTestProject.ui.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import ru.guzenko.HaulmontTestProject.backend.entity.CreditOffer;
import ru.guzenko.HaulmontTestProject.backend.service.*;
import ru.guzenko.HaulmontTestProject.ui.form.CreditOfferForm;

@Route("creditOffer")
public class CreditOfferView extends VerticalLayout implements HasUrlParameter<String> {

    private CreditOfferForm offerForm;
    private final Grid<CreditOffer> offerGrid = new Grid<>(CreditOffer.class);

    private final HorizontalLayout topPanel = new HorizontalLayout();
    private final HorizontalLayout contentPanel = new HorizontalLayout();

    private final BankService bankService;
    private final CreditOfferService creditOfferService;
    private final ClientService clientService;
    private final CreditService creditService;
    private final PaymentService paymentService;

    private Long currentBankId;
    private String currentBankName;
    private Long selectedOfferId;

    public CreditOfferView(BankService bankService, CreditOfferService creditOfferService, ClientService clientService, CreditService creditService, PaymentService paymentService) {
        this.bankService = bankService;
        this.creditOfferService = creditOfferService;
        this.clientService = clientService;
        this.creditService = creditService;
        this.paymentService = paymentService;
    }

    @Override
    public void setParameter(BeforeEvent event, String parameter) {
        currentBankName = parameter;
        currentBankId = bankService.findByBankName(currentBankName).getId();

        setSizeFull();


        configureTopPanel();
        configureContentPanel();

        add(topPanel);
        add(contentPanel);
    }

    private void configureTopPanel() {
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

    private void configureContentPanel() {
        configureGrid();

        Button addButton = new Button("+ Add new credit offer", click -> addCreditOffer());
        Button showDetailsButton = new Button("Show credit details");
        showDetailsButton.setEnabled(false);
        HorizontalLayout toolBar = new HorizontalLayout(addButton, showDetailsButton);

        offerGrid.addSelectionListener(selectionEvent -> {
            if (!offerGrid.asSingleSelect().isEmpty()) {
                showDetailsButton.setEnabled(true);

                selectedOfferId = offerGrid.asSingleSelect().getValue().getId();
            } else {
                showDetailsButton.setEnabled(false);
            }
        });

        showDetailsButton.addClickListener(click -> showDetailsButton.getUI()
                .ifPresent(ui -> ui.navigate(PaymentView.class, selectedOfferId)));

        offerForm = new CreditOfferForm(clientService.findAll(currentBankName), creditService.findAll(currentBankId));
        offerForm.addListener(CreditOfferForm.SaveEvent.class, this::saveCreditOffer);
        offerForm.addListener(CreditOfferForm.DeleteEvent.class, this::deleteCreditOffer);
        offerForm.addListener(CreditOfferForm.CloseEvent.class, e -> closeEditor());

        add(toolBar, offerGrid, offerForm);
    }

    private void configureGrid() {
        /*offerGrid.setWidthFull();
        offerForm.setHeight("500px");*/
        //offerForm.setSizeFull();

        offerGrid.removeColumnByKey("paymentSchedule");
        offerGrid.setColumns("client", "credit", "creditSum");

        offerGrid.getColumns().forEach(col -> col.setAutoWidth(true));

        offerGrid.asSingleSelect().addValueChangeListener(event -> editCreditOffer(event.getValue()));
    }

    private void deleteCreditOffer(CreditOfferForm.DeleteEvent event) {
        creditOfferService.delete(event.getCreditOffer());
        updateList();
        closeEditor();
    }

    private void saveCreditOffer(CreditOfferForm.SaveEvent event) {
        CreditOffer savedCreditOffer = event.getCreditOffer();

        creditOfferService.save(savedCreditOffer);

        paymentService.createPaymentList(savedCreditOffer);

        updateList();
        closeEditor();
    }

    private void addCreditOffer() {
        offerGrid.asSingleSelect().clear();
        editCreditOffer(new CreditOffer());
    }


    private void editCreditOffer(CreditOffer offer) {
        if (offer == null) {
            closeEditor();
        } else {
            offerForm.setCreditOffer(offer);
            offerForm.setVisible(true);
            addClassName("editing");
        }
    }

    private void closeEditor() {
        offerForm.setCreditOffer(null);
        offerGrid.setVisible(false);
        removeClassName("editing");
    }

    private void updateList() {
        offerGrid.setItems(creditOfferService.findAll(currentBankId));
    }
}
