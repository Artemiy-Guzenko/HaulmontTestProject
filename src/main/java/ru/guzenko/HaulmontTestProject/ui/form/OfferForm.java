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
import com.vaadin.flow.data.converter.StringToLongConverter;
import com.vaadin.flow.shared.Registration;
import ru.guzenko.HaulmontTestProject.backend.entity.Client;
import ru.guzenko.HaulmontTestProject.backend.entity.Credit;
import ru.guzenko.HaulmontTestProject.backend.entity.CreditOffer;

import java.util.List;

public class OfferForm extends FormLayout {

    ComboBox<Client> client = new ComboBox<>("Client");
    ComboBox<Credit> credit = new ComboBox<>("Credit");
    TextField creditSum = new TextField("Credit Sum");
    TextField creditPeriod = new TextField("Credit Period");

    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button close = new Button("Cancel");

    Binder<CreditOffer> binder = new Binder<>(CreditOffer.class);
    private CreditOffer creditOffer;

    public OfferForm(List<Client> clients, List<Credit> credits) {

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
                createButtonsLayout()
        );
    }

    public void setOffer(CreditOffer creditOffer) {
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
        delete.addClickListener(click -> fireEvent(new DeleteEvent(this, creditOffer)));
        close.addClickListener(click -> fireEvent(new CloseEvent(this)));

        binder.addStatusChangeListener(event -> save.setEnabled(binder.isValid()));

        return new HorizontalLayout(save, delete, close);
    }

    private void validateAndSave() {
        try {
            binder.writeBean(creditOffer);
            fireEvent(new SaveEvent(this, creditOffer));
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    //Events
    public static abstract class OfferFormEvent extends ComponentEvent<OfferForm> {
        private CreditOffer offer;

        protected OfferFormEvent(OfferForm source, CreditOffer offer) {
            super(source, false);
            this.offer = offer;
        }

        public CreditOffer getOffer() {
            return offer;
        }
    }

    public static class SaveEvent extends OfferFormEvent {
        SaveEvent(OfferForm source, CreditOffer offer) {
            super(source, offer);
        }
    }

    public static class DeleteEvent extends OfferFormEvent {
        DeleteEvent(OfferForm source, CreditOffer offer) {
            super(source, offer);
        }
    }

    public static class CloseEvent extends OfferFormEvent {
        CloseEvent(OfferForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
