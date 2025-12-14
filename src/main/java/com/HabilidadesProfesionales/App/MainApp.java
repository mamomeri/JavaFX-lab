package com.HabilidadesProfesionales.App;

import com.HabilidadesProfesionales.Examples.ButtonExample.ButtonManualExample;
import com.HabilidadesProfesionales.Examples.ButtonExampleFXML.ButtonFXMLExample;
import com.HabilidadesProfesionales.Examples.LoginExampleLayoutFXML.LoginExampleLayoutFXMLExample;
import com.HabilidadesProfesionales.Examples.ScenesAndDrawingStuff.DrawingStuffExample;
import com.HabilidadesProfesionales.Examples.Stages.StageExample;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Objects;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) {

        Navigator.init(stage);

        // === ELIGE QUÉ PROBAR (UNA A LA VEZ) ===

        /*

            WindowSpec.SMALL
            WindowSpec.MEDIUM
            WindowSpec.LARGE
            WindowSpec.fixed(width, height) //sin capacidad de maximizar
            WindowSpec.maximized()
            WindowSpec.fullscreen()
            WindowSpec.of(width, height) // ✅ TAMAÑO CUSTOM con control de resizable
        * */
        //TUTORIAL
        //showStageExample();
        //showDrawingStuffExample();
        //ejemplos propios
        showLoginExampleLayoutFXMLExample();
        //showButtonManualExample();
        //showButtonFXMLExample();

        //Usa MAVEN para obtener la ruta
        Image icono = new Image(Objects.requireNonNull(MainApp.class.getResourceAsStream(
                "/Images/logo.png"
        )));
        stage.getIcons().add(icono);
        stage.setTitle("JavaFX Lab");
        /**
         stage.setFullScreen(true);
         stage.getScene().setFill(Color.BURLYWOOD);
         stage.setFullScreenExitHint("Por favor preciona q \n para cambiar a modo ventana\n");
         stage.setFullScreenExitKeyCombination(KeyCombination.keyCombination("q"));
         */

        stage.show();
    }



    /* =====================================================
       EJEMPLOS TRATABAJADOS (centralizada)
       ===================================================== */
    //TUTORIAL https://www.youtube.com/watch?v=As7TEjqJ3Ao&list=PLZPZq0r_RZOM-8vJA3NQFZB7JroDcMwev
    private void showStageExample(){
        Navigator.show(new StageExample().view(), WindowSpec.SMALL);
    }

    private void showDrawingStuffExample(){
        Navigator.show(new DrawingStuffExample().view(), WindowSpec.of(600,600));
    }

    //PROPIOS
    private void showLoginExampleLayoutFXMLExample(){
        Navigator.show(new LoginExampleLayoutFXMLExample().view(), WindowSpec.LARGE);
    }
    private void showButtonManualExample(){
        Navigator.show(new ButtonManualExample().view(), WindowSpec.SMALL);
    }
    private void showButtonFXMLExample(){
        Navigator.show(new ButtonFXMLExample().view(), WindowSpec.SMALL);
    }



    public static void main(String[] args) {
        launch(args);
    }
}
