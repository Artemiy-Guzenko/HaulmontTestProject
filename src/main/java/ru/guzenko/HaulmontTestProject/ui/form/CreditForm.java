package ru.guzenko.HaulmontTestProject.ui.form;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.converter.StringToDoubleConverter;
import com.vaadin.flow.shared.Registration;
import ru.guzenko.HaulmontTestProject.backend.entity.Credit;

import java.util.List;

public class CreditForm extends FormLayout {

    TextField creditLimit = new TextField("Credit Limit");
    TextField interestRate = new TextField("Interest rate");

    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button close = new Button("Cancel");

    Binder<Credit> binder = new Binder<>(Credit.class);
    private Credit credit;

    public CreditForm(List<Credit> all) {
        addClassName("credit-form");

        binder.forField(creditLimit)
                .withConverter(new StringToDoubleConverter(0.0, "doubles only"))
                .bind(Credit::getCreditLimit, Credit::setCreditLimit);
        binder.forField(interestRate)
                .withConverter(new StringToDoubleConverter(0.0, "doubles only"))
                .bind(Credit::getInterestRate, Credit::setInterestRate);
        binder.bindInstanceFields(this);

        add(
                creditLimit,
                interestRate,
                createButtonsLayout()
        );
    }

    public void setCredit(Credit credit) {
        this.credit = credit;
        binder.readBean(credit);
    }

    private Component createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        save.addClickListener(click -> validateAndSave());
        delete.addClickListener(click -> fireEvent(new CreditForm.DeleteEvent(this, credit)));
        close.addClickListener(click -> fireEvent(new CreditForm.CloseEvent(this)));

        binder.addStatusChangeListener(event -> save.setEnabled(binder.isValid()));

        return new HorizontalLayout(save, delete, close);
    }

    private void validateAndSave() {

        try {
            binder.writeBean(credit);
            fireEvent(new CreditForm.SaveEvent(this, credit));
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    // Events
    public static abstract class CreditFormEvent extends ComponentEvent<CreditForm> {
        private Credit credit;

        protected CreditFormEvent(CreditForm source, Credit credit) {
            super(source, false);
            this.credit = getSource().credit;
        }

        public Credit getCredit() {
            return credit;
        }
    }

    public static class SaveEvent extends CreditForm.CreditFormEvent {
        SaveEvent(CreditForm source, Credit credit) {
            super(source, credit);
        }
    }

    public static class DeleteEvent extends CreditForm.CreditFormEvent {
        DeleteEvent(CreditForm source, Credit credit) {
            super(source, credit);
        }

    }

    public static class CloseEvent extends CreditForm.CreditFormEvent {
        CloseEvent(CreditForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }

}
