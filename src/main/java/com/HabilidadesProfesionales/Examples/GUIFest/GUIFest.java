package com.HabilidadesProfesionales.Examples.GUIFest;

import com.HabilidadesProfesionales.App.IRawExample;
import com.HabilidadesProfesionales.Examples.GUIFest.View.*;
import javafx.scene.Parent;



public class GUIFest implements IRawExample {



    @Override
    public String name() {
        return "GUIFest";
    }

    @Override
    public Parent view() {
        /** Ejemplo: CheckBox */
        // return new CheckBoxView().load();

        /** Ejemplo: ChoiceBox */
        // return new ChoiceBoxView().load();

        /** Ejemplo: ColorPicker */
        // return new ColorPickerView().load();

        /** Ejemplo: DatePicker */
        //return new DatePickerView().load();

        /** Ejemplo: ImageView */
        //return new ImageViewView().load();

        /** Ejemplo: ListView */
        // return new ListViewView().load();

        /** Ejemplo: MenuBar */
         return new MenuBarView().load();

        /** Ejemplo: ProgressBar */
        // return new ProgressBarView().load();

        /** Ejemplo: RadioButtons */
        //return new RadioButtonsView().load();

        /** Ejemplo: Slider */
        // return new SliderView().load();

        /** Ejemplo: Spinner */
        // return new SpinnerView().load();

        /** Ejemplo: TextField */
         //return new TextFieldView().load();

        /** Ejemplo: TreeView */
        // return new TreeViewView().load();

        /** âš ï¸ Selecciona UNO descomentando */
        //return null;
    }
}

