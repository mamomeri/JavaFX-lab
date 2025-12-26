package com.HabilidadesProfesionales.Examples.CommunicationBetweenControllers;

import com.HabilidadesProfesionales.App.IRawExample;
import com.HabilidadesProfesionales.Examples.CommunicationBetweenControllers.View.ComunicationControllerView1;
import javafx.scene.Parent;

public class CommunicationControllers implements IRawExample {
    @Override
    public String name() {
        return "CommunicationControllers";
    }

    @Override
    public Parent view() {
        return new ComunicationControllerView1().load();
    }
}

