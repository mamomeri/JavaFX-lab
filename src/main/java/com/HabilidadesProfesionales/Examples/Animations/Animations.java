package com.HabilidadesProfesionales.Examples.Animations;

import com.HabilidadesProfesionales.App.IRawExample;
import com.HabilidadesProfesionales.Examples.Animations.View.AnimationsView;
import javafx.scene.Parent;

public class Animations implements IRawExample {
    @Override
    public String name() {
        return "Animations";
    }

    @Override
    public Parent view() {
        return new AnimationsView().load();
    }
}
