package ru.guzenko.HaulmontTestProject.ui.view.layout;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import ru.guzenko.HaulmontTestProject.backend.entity.Bank;
import ru.guzenko.HaulmontTestProject.backend.entity.Client;
import ru.guzenko.HaulmontTestProject.backend.service.BankService;
import ru.guzenko.HaulmontTestProject.backend.service.ClientService;
import ru.guzenko.HaulmontTestProject.ui.form.ClientForm;

public class ClientLayout extends VerticalLayout {

    private ClientForm clientForm;
    private Grid<Client> clientGrid = new Grid<>(Client.class);
    private TextField filterText = new TextField();
    private Label label;
    private final String currentBankName;
    private Long currentBankId;

    private final ClientService clientService;
    private final BankService bankService;


    public ClientLayout(ClientService clientService, BankService bankService, String bankName) {
        this.clientService = clientService;
        this.bankService = bankService;
        this.currentBankName = bankName;

        addClassName("client-view");
        setWidthFull();
        setHeight("550px");

        configureGrid();
        configureLabel();

        clientForm = new ClientForm(clientService.findAll(bankName));
        clientForm.addListener(ClientForm.SaveEvent.class, this::saveClient);
        clientForm.addListener(ClientForm.DeleteEvent.class, this::deleteClient);
        clientForm.addListener(ClientForm.CloseEvent.class, e -> closeEditor());

        Div content = new Div(clientGrid, clientForm);
        content.addClassName("content");
        content.setSizeFull();


        add(label, getToolBar(), clientGrid, clientForm);
        updateList();
        closeEditor();
    }

    private void configureGrid() {
        clientGrid.addClassName("client-grid");
        clientGrid.setSizeFull();
        clientGrid.setColumns("lastName", "firstName", "middleName", "phoneNumber", "email", "passport");

        clientGrid.getColumns().forEach(col -> col.setAutoWidth(true));

        clientGrid.asSingleSelect().addValueChangeListener(event -> editClient(event.getValue()));
    }

    private void configureLabel() {
        String text = "Our clients";
        label = new Label(text);
    }

    private void deleteClient(ClientForm.DeleteEvent event) {
        clientService.delete(event.getClient());
        updateList();
        closeEditor();
    }

    private void saveClient(ClientForm.SaveEvent event) {
        clientService.save(event.getClient());
        updateList();
        closeEditor();
    }

    private HorizontalLayout getToolBar() {
        filterText.setPlaceholder("Filter by lastname...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        Button addButton = new Button("+ Add new client", click -> addClient());

        HorizontalLayout toolbar = new HorizontalLayout(filterText, addButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }


    private void addClient() {
        clientGrid.asSingleSelect().clear();

        Client newClient = new Client();
        Bank currentBank = bankService.findByBankName(currentBankName);
        newClient.setBank(currentBank);

        editClient(newClient);
    }


    private void editClient(Client client) {
        if (client == null) {
            closeEditor();
        } else {
            clientForm.setClient(client);
            clientForm.setVisible(true);
            addClassName("editing");
        }
    }

    private void closeEditor() {
        clientForm.setClient(null);
        clientForm.setVisible(false);
        removeClassName("editing");
    }

    private void updateList() {
        clientGrid.setItems(clientService.findAll(filterText.getValue(), currentBankName));
    }

}
