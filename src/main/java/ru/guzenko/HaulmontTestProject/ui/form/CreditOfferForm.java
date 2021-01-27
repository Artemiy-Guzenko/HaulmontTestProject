package ru.guzenko.HaulmontTestProject.ui.form;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.converter.StringToDoubleConverter;
import com.vaadin.flow.data.converter.StringToLongConverter;
import com.vaadin.flow.shared.Registration;
import ru.guzenko.HaulmontTestProject.backend.entity.Client;
import ru.guzenko.HaulmontTestProject.backend.entity.Credit;
import ru.guzenko.HaulmontTestProject.backend.entity.CreditOffer;
import ru.guzenko.HaulmontTestProject.backend.entity.Payment;

import java.util.ArrayList;
import java.util.List;

public class CreditOfferForm extends FormLayout {

    ComboBox<Client> client = new ComboBox<>("Client");
    ComboBox<Credit> credit = new ComboBox<>("Credit");
    TextField creditSum = new TextField("Credit Sum");
    TextField creditPeriod = new TextField("Credit Period");

    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button close = new Button("Cancel");

    Binder<CreditOffer> binder = new Binder<>(CreditOffer.class);
    private CreditOffer creditOffer;

    public CreditOfferForm(List<Client> clients, List<Credit> credits) {
        addClassName("credit-form");

        binder.forField(creditSum)
                .withNullRepresentation("")
                .withConverter(new StringToLongConverter("Only Longs"))
                .bind(CreditOffer::getCreditSum, CreditOffer::setCreditSum);

        binder.forField(creditPeriod)
                .withNullRepresentation("")
                .withConverter(new StringToLongConverter("Only Long"))
                .bind(CreditOffer::getCreditPeriod, CreditOffer::setCreditPeriod);

        binder.bindInstanceFields(this);
        client.setItems(clients);
        client.setItemLabelGenerator(Client::toString);
        credit.setItems(credits);
        credit.setItemLabelGenerator(Credit::toString);

        add(
                client,
                credit,
                creditSum,
                creditPeriod,
                createButtonsLayout());
    }

    public void setCreditOffer(CreditOffer creditOffer) {
        this.creditOffer = creditOffer;
        binder.readBean(creditOffer);
    }

    private Component createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        save.addClickListener(click -> validateAndSave());
        delete.addClickListener(click -> fireEvent(new CreditOfferForm.DeleteEvent(this, creditOffer)));
        close.addClickListener(click -> fireEvent(new CreditOfferForm.CloseEvent(this)));

        binder.addStatusChangeListener(event -> save.setEnabled(binder.isValid()));

        return new HorizontalLayout(save, delete, close);
    }

    private void validateAndSave() {
        try {
            binder.writeBean(creditOffer);
            fireEvent(new CreditOfferForm.SaveEvent(this, creditOffer));
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    // Events
    public static abstract class CreditOfferFormEvent extends ComponentEvent<CreditOfferForm> {
        private CreditOffer creditOffer;

        protected CreditOfferFormEvent(CreditOfferForm source, CreditOffer creditOffer) {
            super(source, false);
            this.creditOffer = creditOffer;
        }

        public CreditOffer getCreditOffer() {
            return creditOffer;
        }
    }

    public static class SaveEvent extends CreditOfferForm.CreditOfferFormEvent {
        SaveEvent(CreditOfferForm source, CreditOffer creditOffer) {
            super(source, creditOffer);
        }
    }

    public static class DeleteEvent extends CreditOfferForm.CreditOfferFormEvent {
        DeleteEvent(CreditOfferForm source, CreditOffer creditOffer) {
            super(source, creditOffer);
        }

    }

    public static class CloseEvent extends CreditOfferForm.CreditOfferFormEvent {
        CloseEvent(CreditOfferForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
