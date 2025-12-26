package com.HabilidadesProfesionales.Examples.KeyEvent;

import com.HabilidadesProfesionales.App.IRawExample;
import com.HabilidadesProfesionales.Examples.KeyEvent.View.KeyEventView;
import javafx.scene.Parent;

public class KeyEvent implements IRawExample {
    @Override
    public String name() {
        return "KeyEvent";
    }

    @Override
    public Parent view() {
        return new KeyEventView().load();
    }
}
