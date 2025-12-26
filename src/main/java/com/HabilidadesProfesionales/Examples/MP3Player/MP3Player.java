package com.HabilidadesProfesionales.Examples.MP3Player;

import com.HabilidadesProfesionales.App.IRawExample;
import com.HabilidadesProfesionales.Examples.MP3Player.View.MP3PlayerView;
import javafx.scene.Parent;

public class MP3Player implements IRawExample {

    @Override
    public String name() {
        return "MP3Player";
    }

    @Override
    public Parent view() {
        return new MP3PlayerView().load();
    }
}
