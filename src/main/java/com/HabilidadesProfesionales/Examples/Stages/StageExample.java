package com.HabilidadesProfesionales.Examples.Stages;

import com.HabilidadesProfesionales.App.IRawExample;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class StageExample implements IRawExample {

    @Override
    public String name() {
        return "StageExample";
    }

    @Override
    public Parent view() {
        // Scene-Graph (Ã¡rbol de nodos)
        // root node (branch node): Group (contenedor)
        Group root = new Group();

        // leaf nodes (hojas): Label y Button
        Label title = new Label("Stage / Scene / Scene-Graph");
        title.setLayoutX(20);
        title.setLayoutY(20);

        Button btn = new Button("Soy un leaf node");
        btn.setLayoutX(20);
        btn.setLayoutY(60);

        root.getChildren().addAll(title, btn);

        // âœ… devolvemos el ROOT del scene-graph
        // (Navigator crea la Scene y la pone en el Stage)
        return root;
    }
}

