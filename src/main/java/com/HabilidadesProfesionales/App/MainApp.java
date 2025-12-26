package com.HabilidadesProfesionales.App;

import com.HabilidadesProfesionales.Examples.Animations.Animations;
import com.HabilidadesProfesionales.Examples.BlurModal.BlurModalExample;
import com.HabilidadesProfesionales.Examples.ButtonExample.ButtonManualExample;
import com.HabilidadesProfesionales.Examples.ButtonExampleFXML.ButtonFXMLExample;
import com.HabilidadesProfesionales.Examples.CSSstyling.CSSstyling;
import com.HabilidadesProfesionales.Examples.CommunicationBetweenControllers.CommunicationControllers;
import com.HabilidadesProfesionales.Examples.EventHandlingSceneBuilder.EventHandlingSceneBuilderExample;
import com.HabilidadesProfesionales.Examples.GUIFest.GUIFest;
import com.HabilidadesProfesionales.Examples.KeyEvent.KeyEvent;
import com.HabilidadesProfesionales.Examples.LogOutExitButton.LogOutExitButton;
import com.HabilidadesProfesionales.Examples.LoginExampleLayoutFXML.LoginExampleLayoutFXMLExample;
import com.HabilidadesProfesionales.Examples.MP3Player.MP3Player;
import com.HabilidadesProfesionales.Examples.MP3Player.View.MP3PlayerView;
import com.HabilidadesProfesionales.Examples.MVCCompleteDesing.MVCCompleteDesignExample;
import com.HabilidadesProfesionales.Examples.MediaView.MediaView;
import com.HabilidadesProfesionales.Examples.ScenesAndDrawingStuff.DrawingStuffExample;
import com.HabilidadesProfesionales.Examples.Stages.StageExample;
import com.HabilidadesProfesionales.Examples.SwitchScenes.SwitchScenes;
import com.HabilidadesProfesionales.Examples.WebBrowser.WebBrowser;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.Objects;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) {

        stage.setOnCloseRequest(event -> {
            event.consume();
            onLogout(stage);
        });
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent arg0) {
                Platform.exit();
                System.exit(0);
            }
        });

        Navigator.init(stage);

        // === ELIGE QUÃ‰ PROBAR (UNA A LA VEZ) ===

        /*

            WindowSpec.SMALL
            WindowSpec.MEDIUM
            WindowSpec.LARGE
            WindowSpec.fixed(width, height) //sin capacidad de maximizar
            WindowSpec.maximized()
            WindowSpec.fullscreen()
            WindowSpec.of(width, height) // âœ… TAMAÃ‘O CUSTOM con control de resizable
        * */
        //TUTORIAL
        //showStageExample();
        //showDrawingStuffExample();
        //showEventHandlingSceneBuilderExample();
        //showCSSstylingExample();
        //showSwitchEsceneExample();
        //showBlurModalExample();
        //showCommunicationControllersExample();
        //showLogoutExample();
        //showGUIFestExample();
        //ejemplos propios

        //showMVCCompleteDesignExample();
        //showLoginExampleLayoutFXMLExample();
        //showButtonManualExample();
        //showButtonFXMLExample();
        //showKeyEventExample();
        //showAnimationsExample();
        //showMediaViewExample();
        //showWebBrowserExample();
        showMP3PlayerExample();
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
    private void showEventHandlingSceneBuilderExample(){
        Navigator.show(new EventHandlingSceneBuilderExample().view(), WindowSpec.MEDIUM);
    }
    private void showDrawingStuffExample(){
        Navigator.show(new DrawingStuffExample().view(), WindowSpec.of(600,600));
    }
    private void showCSSstylingExample(){
        Navigator.show(new CSSstyling().view(), WindowSpec.MEDIUM);
    }
    private void showSwitchEsceneExample(){
        Navigator.show(new SwitchScenes().view(), WindowSpec.MEDIUM);
    }

    private void showCommunicationControllersExample(){
        Navigator.show(new CommunicationControllers().view(), WindowSpec.LARGE);
    }
    private void showLogoutExample(){
        Navigator.show(new LogOutExitButton().view(), WindowSpec.LARGE);
    }
    private void showGUIFestExample(){
        Navigator.show(new GUIFest().view(), WindowSpec.LARGE);
    }
    private void showKeyEventExample(){
        Navigator.show(new KeyEvent().view(), WindowSpec.LARGE);
    }
    private void showAnimationsExample(){
        Navigator.show(new Animations().view(), WindowSpec.LARGE);
    }
    private void showMediaViewExample(){
        Navigator.show(new MediaView().view(), WindowSpec.LARGE);
    }
    private void showWebBrowserExample(){
        Navigator.show(new WebBrowser().view(), WindowSpec.LARGE);
    }
    private void showMP3PlayerExample(){
        Navigator.show(new MP3Player().view(), WindowSpec.LARGE);
    }
    //PROPIOS
    private void showBlurModalExample(){
        Navigator.show(new BlurModalExample().view(), WindowSpec.MEDIUM);
    }

    private void showMVCCompleteDesignExample(){
        Navigator.show(new MVCCompleteDesignExample().view(), WindowSpec.of(600,600));
    }
    private void showLoginExampleLayoutFXMLExample(){
        Navigator.show(new LoginExampleLayoutFXMLExample().view(), WindowSpec.LARGE);
    }
    private void showButtonManualExample(){
        Navigator.show(new ButtonManualExample().view(), WindowSpec.SMALL);
    }
    private void showButtonFXMLExample(){
        Navigator.show(new ButtonFXMLExample().view(), WindowSpec.SMALL);
    }


    public void onLogout(Stage stage){

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setHeaderText("Estas a punto de desloguearte");
        alert.setContentText("Quieres guardar antes de salir?");

        if(alert.showAndWait().get() == ButtonType.OK){

            System.out.println("You succesfully logged out!");
            stage.close();
        }



    }
    public static void main(String[] args) {
        launch(args);
    }
}

