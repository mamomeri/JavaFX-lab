package com.HabilidadesProfesionales.Examples.GUIFest.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class TextFieldController {
    @FXML
    Label myLabel;
    @FXML
    TextField myTextField;

    int age;

    public void onSubmit(ActionEvent event){
        try{
        age = Integer.parseInt(myTextField.getText());
        System.out.println("Your age is "+ String.valueOf(age)+" years old");

        if(age < 18){
            myLabel.setText("Eres menor de edad");
        }
        else{
            myLabel.setText("Eres mayor de edad, puedes pasar!");
        }


        } catch (NumberFormatException e) {
            System.out.println("FORMATO DE ENTRADA INCORRECTO "+ e.toString());
            myLabel.setText("Only numbers plis");
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}

