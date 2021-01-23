package ru.guzenko.HaulmontTestProject.ui.test;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import lombok.RequiredArgsConstructor;
import ru.guzenko.HaulmontTestProject.backend.entity.Bank;
import ru.guzenko.HaulmontTestProject.backend.service.BankService;
import ru.guzenko.HaulmontTestProject.ui.test.BankForm;

import javax.annotation.PostConstruct;

@Route("aaa")
@RequiredArgsConstructor
public class MainLayout extends VerticalLayout {

    private final BankService bankService;

    private BankForm bankForm;


    Grid<Bank> bankGrid = new Grid<>(Bank.class);
    Label label;




    @PostConstruct
    void init() {
        addClassName("bank-list-view");
        setSizeFull();
        configureBankGrid();
        configureLabel();

        bankForm = new BankForm();

        Div content = new Div(bankGrid, bankForm);
        content.addClassName("content");
        content.setSizeFull();

        add(label, content);
    }

    private void configureLabel() {
        String text = "asdfgasgasdgasdgasdga";

        label = new Label(text);
    }

    /*public MainLayout(BankService bankService) {
        String text = "asdfgasgasdgasdgasdga";

        label = new Label(text);

        this.bankService = bankService;
        addClassName("bank-list-view");
        setSizeFull();

        configureBankGrid();



        add(label ,bankGrid);




    }*/


    private void configureBankGrid() {
        bankGrid.addClassName("bank-grid");
        bankGrid.setSizeFull();
        bankGrid.setColumns("bankName");

        bankGrid.setItems(bankService.findAll());

        /*bankGrid.addColumn(bank ->
                bank.getClientList().size())
                .setHeader("Amount of clients");
        bankGrid.addColumn(bank ->
                bank.getCreditList().size())
                .setHeader("Amount of loan options");*/
    }

}
