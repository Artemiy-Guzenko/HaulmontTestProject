package ru.guzenko.HaulmontTestProject.ui.view.layout;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import ru.guzenko.HaulmontTestProject.backend.entity.Bank;
import ru.guzenko.HaulmontTestProject.backend.entity.Credit;
import ru.guzenko.HaulmontTestProject.backend.service.BankService;
import ru.guzenko.HaulmontTestProject.backend.service.CreditService;
import ru.guzenko.HaulmontTestProject.ui.form.CreditForm;

public class CreditLayout extends VerticalLayout {

    private CreditForm creditForm;
    private Grid<Credit> creditGrid = new Grid<>(Credit.class);
    private Label label;

    private final Long currentBankId;
    private final CreditService creditService;
    private final BankService bankService;

    public CreditLayout(CreditService creditService, BankService bankService, Long currentBankId) {
        this.creditService = creditService;
        this.bankService = bankService;
        this.currentBankId = currentBankId;

        addClassName("credit-view");
        setWidth("400px");
        setHeight("450px");

        configureGrid();
        configureLabel();

        creditForm = new CreditForm(creditService.findAll(currentBankId));
        creditForm.addListener(CreditForm.SaveEvent.class, this::saveCredit);
        creditForm.addListener(CreditForm.DeleteEvent.class, this::deleteCredit);
        creditForm.addListener(CreditForm.CloseEvent.class, e -> closeEditor());

        Div content = new Div(creditGrid, creditForm);
        content.addClassName("content");
        content.setSizeFull();

        add(label, getToolBar(), content);
        updateList();
        closeEditor();
    }

    private void configureGrid() {
        creditGrid.addClassName("credit-grid");
        creditGrid.setSizeFull();
        creditGrid.setColumns("creditLimit", "interestRate");

        creditGrid.getColumns().forEach(col -> col.setAutoWidth(true));

        creditGrid.asSingleSelect().addValueChangeListener(event -> editCredit(event.getValue()));
    }

    private void configureLabel() {
        String text = "Available loan options";
        label = new Label(text);
    }

    private void deleteCredit(CreditForm.DeleteEvent event) {
        creditService.delete(event.getCredit());
        updateList();
        closeEditor();
    }

    private void saveCredit(CreditForm.SaveEvent event) {
        creditService.save(event.getCredit());
        updateList();
        closeEditor();
    }
    private HorizontalLayout getToolBar() {
        Button addButton = new Button("+ Add new credit", click -> addCredit());

        HorizontalLayout toolbar = new HorizontalLayout(addButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }


    private void addCredit() {
        creditGrid.asSingleSelect().clear();

        Credit newCredit = new Credit();
        Bank currentBank = bankService.findByBankId(currentBankId).get();
        newCredit.setBank(currentBank);

        editCredit(newCredit);
    }


    private void editCredit(Credit credit) {
        if (credit == null) {
            closeEditor();
        } else {
            creditForm.setCredit(credit);
            creditForm.setVisible(true);
            addClassName("editing");
        }
    }

    private void closeEditor() {
        creditForm.setCredit(null);
        creditForm.setVisible(false);
        removeClassName("editing");
    }

    private void updateList() {
        creditGrid.setItems(creditService.findAll(currentBankId));
    }
}
