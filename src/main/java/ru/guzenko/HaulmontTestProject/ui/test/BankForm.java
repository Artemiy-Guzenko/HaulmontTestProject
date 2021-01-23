package ru.guzenko.HaulmontTestProject.ui.test;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;
import ru.guzenko.HaulmontTestProject.backend.entity.Bank;

public class BankForm extends FormLayout {

    TextField bankName = new TextField("Bank name");

    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button close = new Button("Close");

    Binder<Bank> binder = new BeanValidationBinder<>(Bank.class);
    private Bank bank;


    public BankForm() {
        addClassName("bank-form");

        binder.bindInstanceFields(this);

        add(
                bankName,
                createButtonLayout()
        );
    }

    public void setBank(Bank bank) {
        binder.setBean(bank);
    }


    private Component createButtonLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);


        save.addClickListener(click -> validateAndSave());
        delete.addClickListener(click -> fireEvent(new DeleteEvent(this, bank)));
        close.addClickListener(click -> fireEvent(new CloseEvent(this)));

        binder.addStatusChangeListener(event -> save.setEnabled(binder.isValid()));

        return new HorizontalLayout(save, delete, close);
    }


    private void validateAndSave() {
        try {
            binder.writeBean(bank);
            fireEvent(new SaveEvent(this, bank));
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    // Events
    public static abstract class BankFormEvent extends ComponentEvent<BankForm> {
        private Bank bank;

        protected BankFormEvent(BankForm source, Bank bank) {
            super(source, false);
            this.bank = bank;
        }

        public Bank getContact() {
            return bank;
        }
    }

    public static class SaveEvent extends BankFormEvent {
        SaveEvent(BankForm source, Bank bank) {
            super(source, bank);
        }
    }

    public static class DeleteEvent extends BankFormEvent {
        DeleteEvent(BankForm source, Bank bank) {
            super(source, bank);
        }

    }

    public static class CloseEvent extends BankFormEvent {
        CloseEvent(BankForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }

}
