package com.HabilidadesProfesionales.Examples.WebBrowser;

import com.HabilidadesProfesionales.App.IRawExample;
import com.HabilidadesProfesionales.Examples.WebBrowser.View.WebBrowserView;
import javafx.scene.Parent;

public class WebBrowser implements IRawExample {
    @Override
    public String name() {
        return "WebBrowser";
    }

    @Override
    public Parent view() {
        return new WebBrowserView().load();
    }
}
