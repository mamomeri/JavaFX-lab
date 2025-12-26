package com.HabilidadesProfesionales.Examples.MVCCompleteDesing;

import com.HabilidadesProfesionales.App.IRawExample;
import com.HabilidadesProfesionales.Examples.MVCCompleteDesing.Model.SpoolerModel;
import com.HabilidadesProfesionales.Examples.MVCCompleteDesing.View.MainView;
import com.HabilidadesProfesionales.Examples.MVCCompleteDesing.View.SecondaryView;
import javafx.scene.Parent;
import javafx.scene.text.Font;

import java.util.Objects;

public class MVCCompleteDesignExample implements IRawExample {

    private static boolean fontsLoaded = false;

    private final SpoolerModel model = new SpoolerModel();

    @Override
    public String name() {
        return "MVCCompleteDesignExample";
    }

    @Override
    public Parent view() {
        loadFontsOnce();

        MainView mainView = new MainView();
        SecondaryView secondaryView = new SecondaryView();

        Parent mainRoot = mainView.load();
        Parent secondaryRoot = secondaryView.load();

        String css = Objects.requireNonNull(
                getClass().getResource("/styles/lab-theme.css"),
                "No encuentro /styles/lab-theme.css"
        ).toExternalForm();

        mainRoot.getStylesheets().add(css);
        secondaryRoot.getStylesheets().add(css);

        mainView.getController().setModel(model);
        secondaryView.getController().setModel(model);

        mainView.getController().setOnGoStats(() ->
                mainRoot.getScene().setRoot(secondaryRoot)
        );

        secondaryView.getController().setOnBack(() ->
                secondaryRoot.getScene().setRoot(mainRoot)
        );

        model.start();
        return mainRoot;
    }

    private static void loadFontsOnce() {
        if (fontsLoaded) return;

        loadOne("/Fonts/Inter/Inter_24pt-Regular.ttf");
        loadOne("/Fonts/Inter/Inter_24pt-Medium.ttf");
        loadOne("/Fonts/Inter/Inter_24pt-SemiBold.ttf");
        loadOne("/Fonts/Inter/Inter_24pt-Bold.ttf");

        loadOne("/Fonts/Bebas_Neue/BebasNeue-Regular.ttf");

        fontsLoaded = true;
    }

    private static void loadOne(String path) {
        var is = MVCCompleteDesignExample.class.getResourceAsStream(path);
        if (is == null) {
            System.out.println("âŒ NO encuentro: " + path);
            return;
        }
        Font f = Font.loadFont(is, 12);
        System.out.println((f == null ? "âŒ No cargÃ³ font: " : "âœ… CargÃ³ font: ") + path +
                (f == null ? "" : " -> " + f.getFamily()));
    }
}

