package ru.guzenko.HaulmontTestProject.ui;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import ru.guzenko.HaulmontTestProject.backend.entity.Bank;
import ru.guzenko.HaulmontTestProject.backend.service.BankService;
import ru.guzenko.HaulmontTestProject.ui.form.BankForm;
import ru.guzenko.HaulmontTestProject.ui.layout.BankLayout;

@Route("")
public class MainLayout extends VerticalLayout {

    BankForm form;
    Grid<Bank> grid = new Grid<>(Bank.class);
    Label label;

    private final BankService bankService;
    private String selectedBankName;

    public MainLayout(BankService bankService) {
        this.bankService = bankService;

        addClassName("banks-view");
        setSizeFull();
        configureGrid();
        configureLabel();

        form = new BankForm(bankService.findAll());
        form.addListener(BankForm.SaveEvent.class, this::saveBank);
        form.addListener(BankForm.DeleteEvent.class, this::deleteBank);
        form.addListener(BankForm.CloseEvent.class, e -> closeEditor());

        VerticalLayout gridLayout = new VerticalLayout(grid);
        gridLayout.setWidth("500px");
        gridLayout.setHeight("500px");
        VerticalLayout formLayout = new VerticalLayout(form);
        formLayout.setWidth("300px");
        formLayout.setHeight("500px");

        HorizontalLayout content = new HorizontalLayout(gridLayout, formLayout);

        add(label, getToolBar(), content);
        updateList();
        closeEditor();
    }

    private void configureGrid() {
        grid.addClassName("bank-grid");
        grid.setSizeFull();
        grid.setColumns("bankName");

        /*grid.addColumn(bank ->
                bank.getClientList().size())
                .setHeader("Amount of clients");
        grid.addColumn(bank ->
                bank.getCreditList().size())
                .setHeader("Amount of loan options");*/


        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(event -> editBank(event.getValue()));
    }

    private void configureLabel() {
        String text = "TEXTTEXTETXETXETXET";
        label = new Label(text);
    }


    private void deleteBank(BankForm.DeleteEvent event) {
        bankService.delete(event.getBank());
        updateList();
        closeEditor();
    }

    private void saveBank(BankForm.SaveEvent event) {
        bankService.save(event.getBank());
        updateList();
        closeEditor();
    }

    private HorizontalLayout getToolBar() {
        Button addButton = new Button("+ Add new bank", click -> addBank());

        Button goToButton = new Button("Go to selected bank ->");
        goToButton.setEnabled(false);

        grid.addSelectionListener(selectionEvent -> {
            if (!grid.asSingleSelect().isEmpty()) {
                goToButton.setEnabled(true);

                selectedBankName = grid.asSingleSelect().getValue().getBankName();
            } else {
                goToButton.setEnabled(false);
            }
        });

        goToButton.addClickListener(click ->
                goToButton
                        .getUI()
                        .ifPresent(ui -> ui.navigate(BankLayout.class, selectedBankName)));


        HorizontalLayout toolbar = new HorizontalLayout(addButton, goToButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }


    private void addBank() {
        grid.asSingleSelect().clear();
        editBank(new Bank());
    }


    private void editBank(Bank bank) {
        if (bank == null) {
            closeEditor();
        } else {
            form.setBank(bank);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    private void closeEditor() {
        form.setBank(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void updateList() {
        grid.setItems(bankService.findAll());
    }

}
