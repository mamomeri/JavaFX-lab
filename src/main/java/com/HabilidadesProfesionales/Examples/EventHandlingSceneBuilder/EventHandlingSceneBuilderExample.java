package com.HabilidadesProfesionales.Examples.EventHandlingSceneBuilder;

import com.HabilidadesProfesionales.App.IRawExample;
import com.HabilidadesProfesionales.App.Navigator;
import com.HabilidadesProfesionales.App.WindowSpec;
import com.HabilidadesProfesionales.Examples.EventHandlingSceneBuilder.View.EventHandlingView;
import javafx.scene.Parent;

public class EventHandlingSceneBuilderExample implements IRawExample {

    @Override
    public String name() {
        return "EventHandlingSceneBuilder";
    }

    @Override
    public Parent view() {
        return new EventHandlingView().load(); // delega la responsabilidad a View
    }


}

