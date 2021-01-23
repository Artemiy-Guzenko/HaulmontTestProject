package ru.guzenko.HaulmontTestProject.ui;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
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
    private String bankName;

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

        Div content = new Div(grid, form);
        content.addClassName("content");
        content.setSizeFull();

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
        /*filterText.setPlaceholder("Filter by name...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());*/

        Button addButton = new Button("+ Add new bank", click -> addBank());

        Button goToButton = new Button("Go to selected bank ->");

        grid.addSelectionListener(selectionEvent -> {
            if (!grid.asSingleSelect().isEmpty()) {
                goToButton.setEnabled(true);

                bankName = grid.asSingleSelect().getValue().getBankName();
            } else {
                goToButton.setEnabled(false);
            }
        });

        goToButton.addClickListener(click ->
                goToButton
                        .getUI()
                        .ifPresent(ui -> ui.navigate(BankLayout.class, bankName)));


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
