package com.HabilidadesProfesionales.Examples.BlurModal.Model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ConfirmOverlayModel {

    private final StringProperty title = new SimpleStringProperty("ConfirmaciÃ³n");
    private final StringProperty message = new SimpleStringProperty("Â¿EstÃ¡s seguro?");
    private final BooleanProperty showing = new SimpleBooleanProperty(false);

    public StringProperty titleProperty() { return title; }
    public StringProperty messageProperty() { return message; }
    public BooleanProperty showingProperty() { return showing; }

    public String getTitle() { return title.get(); }
    public String getMessage() { return message.get(); }
    public boolean isShowing() { return showing.get(); }

    public void setTitle(String value) { title.set(value); }
    public void setMessage(String value) { message.set(value); }
    public void setShowing(boolean value) { showing.set(value); }
}

