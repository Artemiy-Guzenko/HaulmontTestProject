package ru.guzenko.HaulmontTestProject.ui.view.layout;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import ru.guzenko.HaulmontTestProject.backend.entity.CreditOffer;
import ru.guzenko.HaulmontTestProject.backend.service.ClientService;
import ru.guzenko.HaulmontTestProject.backend.service.CreditOfferService;
import ru.guzenko.HaulmontTestProject.backend.service.CreditService;
import ru.guzenko.HaulmontTestProject.backend.service.PaymentService;
import ru.guzenko.HaulmontTestProject.ui.form.OfferForm;
import ru.guzenko.HaulmontTestProject.ui.view.PaymentView;

public class OfferLayout extends VerticalLayout {

    private Long selectedOfferId;
    private final Long currentBankId;
    private final String currentBankName;


    private OfferForm form;
    private Grid<CreditOffer> offerGrid = new Grid<>(CreditOffer.class);

    private final CreditOfferService creditOfferService;
    private final ClientService clientService;
    private final CreditService creditService;
    private final PaymentService paymentService;


    public OfferLayout(CreditOfferService creditOfferService, ClientService clientService,
                       CreditService creditService, PaymentService paymentService,
                       String currentBankName, Long currentBankId) {
        this.creditOfferService = creditOfferService;
        this.clientService = clientService;
        this.creditService = creditService;
        this.paymentService = paymentService;
        this.currentBankName = currentBankName;
        this.currentBankId = currentBankId;


        add(getToolBar());

        configureOfferGrid();
        add(offerGrid);

        form = new OfferForm(clientService.findAll(currentBankName), creditService.findAll(currentBankId));
        form.addListener(OfferForm.SaveEvent.class, this::saveOffer);
        form.addListener(OfferForm.DeleteEvent.class, this::deleteOffer);
        form.addListener(OfferForm.CloseEvent.class, event -> closeEditor());

        add(form);
        updateList();
        closeEditor();
    }

    private void configureOfferGrid() {
        offerGrid.setWidthFull();
        offerGrid.setHeight("300px");

        offerGrid.removeColumnByKey("id");
        offerGrid.removeColumnByKey("paymentSchedule");
        //offerGrid.setColumns("client", "credit", "creditSum", "creditPeriod");
        offerGrid.getColumns().forEach(col -> col.setAutoWidth(true));


        offerGrid.setItems(creditOfferService.findAll(currentBankId));
        offerGrid.asSingleSelect().addValueChangeListener(event -> editOffer(event.getValue()));
    }

    private HorizontalLayout getToolBar() {
        Button addButton = new Button("+ Add new credit offer", click -> addOffer());
        Button goToButton = new Button("Show credit details ->");
        goToButton.setEnabled(false);


        offerGrid.addSelectionListener(selectionEvent -> {
           if (!offerGrid.asSingleSelect().isEmpty()) {
               goToButton.setEnabled(true);

               selectedOfferId = offerGrid.asSingleSelect().getValue().getId();
           } else {
               goToButton.setEnabled(false);
           }
        });

        goToButton.addClickListener(click -> goToButton
                .getUI().ifPresent(ui -> ui.navigate(PaymentView.class, selectedOfferId)));

        HorizontalLayout toolBar = new HorizontalLayout(addButton, goToButton);
        return toolBar;
    }

    private void deleteOffer(OfferForm.DeleteEvent event) {
        creditOfferService.delete(event.getOffer());
        updateList();
        closeEditor();
    }

    private void saveOffer(OfferForm.SaveEvent event) {
        CreditOffer savingOffer = event.getOffer();

        creditOfferService.save(savingOffer);
        paymentService.createPaymentList(savingOffer);

        updateList();
        closeEditor();
    }

    private void addOffer() {
        offerGrid.asSingleSelect().clear();
        editOffer(new CreditOffer());
    }

    private void editOffer(CreditOffer offer) {
        if (offer == null) {
            closeEditor();
        } else {
            form.setOffer(offer);
            form.setVisible(true);
        }
    }

    private void closeEditor() {
        form.setOffer(null);
        form.setVisible(false);
    }

    private void updateList() {
        offerGrid.setItems(creditOfferService.findAll(currentBankId));
    }
}
