package com.HabilidadesProfesionales.Examples.MediaView;

import com.HabilidadesProfesionales.App.IRawExample;
import com.HabilidadesProfesionales.Examples.MediaView.View.MediaViewView;
import javafx.scene.Parent;

public class MediaView implements IRawExample {
    @Override
    public String name() {
        return "MediaView";
    }

    @Override
    public Parent view() {
        return new MediaViewView().load();
    }
}
