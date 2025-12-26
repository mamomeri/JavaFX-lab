package com.HabilidadesProfesionales.Examples.LogOutExitButton;

import com.HabilidadesProfesionales.App.IRawExample;
import com.HabilidadesProfesionales.Examples.LogOutExitButton.View.LogOutExitButtonView;
import javafx.scene.Parent;

public class LogOutExitButton implements IRawExample {
    @Override
    public String name() {
        return "LogOutExitButton";
    }

    @Override
    public Parent view() {
        return new LogOutExitButtonView().load();
    }
}

