package com.HabilidadesProfesionales.Examples.SwitchScenes;

import com.HabilidadesProfesionales.App.IRawExample;
import com.HabilidadesProfesionales.Examples.SwitchScenes.View.SwitchScenesView;
import javafx.scene.Parent;

public class SwitchScenes implements IRawExample {
    @Override
    public String name() {
        return "SwitchScenes";
    }

    @Override
    public Parent view() {
        return new SwitchScenesView().load();
    }
}

