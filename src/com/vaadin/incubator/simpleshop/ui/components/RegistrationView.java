package com.vaadin.incubator.simpleshop.ui.components;

import com.vaadin.incubator.simpleshop.lang.SystemMsg;
import com.vaadin.incubator.simpleshop.ui.controllers.UserController;
import com.vaadin.incubator.simpleshop.ui.controllers.UserController.RegistrationError;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Window.Notification;

public class RegistrationView extends VerticalLayout implements ClickListener {

    private static final long serialVersionUID = -1557284716632681422L;

    private final Label feedbackLabel;
    private final TextField username;
    private final TextField password;
    private final TextField verifyPassword;

    private final Button registerBtn;
    private final Button cancelBtn;

    public RegistrationView() {
        setWidth("100%");
        setMargin(true);
        setSpacing(true);

        feedbackLabel = new Label();
        addComponent(feedbackLabel);

        username = new TextField(SystemMsg.GENERIC_USERNAME.get());
        username.setNullRepresentation("");
        username.setWidth("100%");
        username.focus();
        addComponent(username);

        password = new TextField(SystemMsg.GENERIC_PASSWORD.get());
        password.setSecret(true);
        password.setNullRepresentation("");
        password.setWidth("100%");
        addComponent(password);

        verifyPassword = new TextField(SystemMsg.REGISTER_VERIFY_PASSWORD.get());
        verifyPassword.setSecret(true);
        verifyPassword.setNullRepresentation("");
        verifyPassword.setWidth("100%");
        addComponent(verifyPassword);

        registerBtn = new Button(SystemMsg.REGISTER_REGISTER_BTN.get(), this);
        registerBtn.setSizeUndefined();
        cancelBtn = new Button(SystemMsg.GENERIC_CANCEL.get(), this);
        cancelBtn.setSizeUndefined();

        HorizontalLayout buttonLayout = new HorizontalLayout();
        HorizontalLayout spacer = new HorizontalLayout();
        buttonLayout.setWidth("100%");
        buttonLayout.setSpacing(true);

        buttonLayout.addComponent(spacer);
        buttonLayout.addComponent(cancelBtn);
        buttonLayout.addComponent(registerBtn);

        buttonLayout.setExpandRatio(spacer, 1);

        addComponent(buttonLayout);
    }

    public void buttonClick(ClickEvent event) {
        if (event.getButton().equals(cancelBtn)) {
            username.setValue(null);
            password.setValue(null);
            verifyPassword.setValue(null);
            ((InformationView) getParent()).setCurrentView(new LoginView());
        } else if (event.getButton().equals(registerBtn)) {
            RegistrationError error = UserController.registerUser(
                    (String) username.getValue(), (String) password.getValue(),
                    (String) verifyPassword.getValue());
            if (error.equals(RegistrationError.REGISTRATION_COMPLETED)) {
                getApplication().getMainWindow().showNotification(
                        error.getMessage(), "",
                        Notification.TYPE_TRAY_NOTIFICATION);
                ((InformationView) getParent()).setCurrentView(new LoginView());
            } else {
                feedbackLabel.setValue(error.getMessage());
                password.setValue(null);
                verifyPassword.setValue(null);
            }
        }
    }

}