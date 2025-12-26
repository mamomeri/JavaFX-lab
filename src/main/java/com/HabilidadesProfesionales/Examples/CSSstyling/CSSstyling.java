package com.HabilidadesProfesionales.Examples.CSSstyling;

import com.HabilidadesProfesionales.App.IRawExample;
import com.HabilidadesProfesionales.Examples.CSSstyling.View.CSSstylingView;
import javafx.scene.Parent;

public class CSSstyling implements IRawExample {
    @Override
    public String name() {
        return "CSSstyling";
    }

    @Override
    public Parent view() {
        return new CSSstylingView().load();
    }
}

