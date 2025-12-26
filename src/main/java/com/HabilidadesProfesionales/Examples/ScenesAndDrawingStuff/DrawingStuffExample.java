package com.HabilidadesProfesionales.Examples.ScenesAndDrawingStuff;

import com.HabilidadesProfesionales.App.IRawExample;
import com.HabilidadesProfesionales.Utils.FontUtils;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.awt.*;
import java.io.IOException;

/*
 * NOTA (cÃ³mo adaptar tutoriales JavaFX a este proyecto con Navigator)
 * ------------------------------------------------------------------
 * En este proyecto, Navigator.show(view, spec) crea SIEMPRE una Scene nueva:
 *
 *   stage.setScene(new Scene(view, ...));
 *
 * O sea:
 *   - No existe una "Scene global" persistente.
 *   - Cada navegaciÃ³n reemplaza la Scene completa.
 *
 * Implicaciones prÃ¡cticas:
 * 1) En cada ejemplo (IRawExample.view()):
 *    - SOLO construye el root (Parent) y retorna ese root.
 *    - NO crees Stage ni Scene dentro del ejemplo (el Stage lo maneja Navigator).
 *
 * 2) Si el tutorial configura cosas de la Scene (fill, eventos de teclado/mouse globales):
 *    Como aquÃ­ la Scene se recrea cada vez, esas configuraciones se "pierden" al navegar.
 *    Tienes dos formas correctas de hacerlo:
 *
 *    A) Configurar Scene en Navigator (global por navegaciÃ³n):
 *       - Crea la Scene en una variable, configÃºrala, luego stage.setScene(scene).
 *         Ej:
 *           Scene scene = new Scene(view, spec.width, spec.height);
 *           scene.setFill(Color.LIGHTSKYBLUE);
 *           scene.setOnKeyPressed(...);
 *           stage.setScene(scene);
 *
 *    B) Configurar la Scene desde el root (por ejemplo, en view()):
 *       - Usar root.sceneProperty().addListener(...) para aplicar configuraciÃ³n
 *         cuando el root ya estÃ© montado a la Scene reciÃ©n creada.
 *         Ej:
 *           root.sceneProperty().addListener((obs, oldS, newS) -> {
 *               if (newS == null) return;
 *               newS.setFill(Color.LIGHTSKYBLUE);
 *               newS.setOnMouseDragged(...);
 *           });
 *
 * 3) Eventos de controles (ej: click de un botÃ³n):
 *    - Eso NO depende de la Scene global: se hace directo en el control:
 *        button.setOnAction(e -> ...);
 *    - Esto es lo recomendado para UI normal.
 *
 * Sobre el problema del color:
 * - scene.setFill(...) pinta el "fondo" de la Scene.
 * - Pero si tu root es un Region (Pane/VBox/StackPane/...), puedes ponerle background por CSS
 *   y ese fondo tapa visualmente el fill de la Scene.
 * - Por eso, para ejemplos tipo "lienzo", suele ser mÃ¡s estable poner:
 *     root.setStyle("-fx-background-color: lightskyblue;");
 *
 * Â¿Pane vs Group?
 * - Pane (y en general layouts/Region):
 *     * Soporta background por CSS (ideal para â€œlienzoâ€).
 *     * Tiene tamaÃ±o/Ã¡rea mÃ¡s estable para capturar eventos en zonas vacÃ­as.
 *     * Mejor para UI + interacciÃ³n.
 *
 * - Group:
 *     * NO hace layout.
 *     * Su tamaÃ±o/bounds dependen de sus hijos (si no hay hijos â€œocupandoâ€ Ã¡rea, puede ser incÃ³modo).
 *     * Ãštil para dibujos con coordenadas (Shapes/Text) cuando controlas X/Y.
 *   Se puede usar, pero si quieres â€œlienzo con fondo y mouse en toda el Ã¡reaâ€,
 *   normalmente envuelves el Group dentro de un Pane/StackPane con background.
 */

/*
 * ACCEDER A LA "Scene PRINCIPAL" DESDE CUALQUIER PARTE EN JAVAFX
 * =============================================================
 *
 * Â¿Por quÃ© no existe un acceso â€œsimple y globalâ€ a la Scene/Stage?
 * - JavaFX separa responsabilidades:
 *     * Stage = ventana del sistema operativo
 *     * Scene = â€œmundoâ€ de nodos dentro del Stage
 *     * Node/Parent/Control = componentes dentro de la Scene
 * - Un Node NO â€œviveâ€ siempre dentro de una Scene:
 *     * Antes de mostrarse, un root puede estar â€œsueltoâ€ (Scene = null)
 *     * Puede moverse a otra Scene (navegaciÃ³n, tabs, popups, etc.)
 *     * Por eso, si intentas usar Scene/Stage como un global duro, te topas con nulls
 *       y con cÃ³digo acoplado/fragil.
 *
 * â€œCartasâ€ (mecanismos) que JavaFX ya trae para resolver esto:
 *
 * 1) Obtener Scene/Stage desde cualquier Node (cuando ya estÃ¡ montado):
 *      Scene s = someNode.getScene();
 *      Stage st = (Stage) s.getWindow();
 *
 * 2) Esperar a que un Node sea agregado a una Scene (cuando al inicio es null):
 *      someNode.sceneProperty().addListener((obs, oldS, newS) -> {
 *          if (newS == null) return;
 *          Stage st = (Stage) newS.getWindow();
 *      });
 *
 * 3) Eventos sin â€œreferencia globalâ€ (la Scene te la da el Event):
 *      someNode.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
 *          Scene s = ((Node) e.getSource()).getScene();
 *      });
 *
 * 4) Acceso a â€œla aplicaciÃ³nâ€ sin pasar referencias por todos lados:
 *    (A) InyecciÃ³n simple (la mÃ¡s limpia):
 *          - pasar Stage/Scene/Services por constructor o setter
 *    (B) Contexto global controlado (Service Locator / AppContext):
 *          - guardar Stage/Scene o servicios en una clase AppContext
 *          - ej: AppContext.getStage(), AppContext.getThemeService(), etc.
 *
 * 5) Cambios globales tÃ­picos que se aplican a la Scene:
 *      - Fondo:
 *          scene.setFill(Color.X);
 *      - CSS global:
 *          scene.getStylesheets().add("app.css");
 *      - Atajos de teclado:
 *          scene.setOnKeyPressed(e -> { ... });
 *
 * Regla prÃ¡ctica:
 * - Si estÃ¡s dentro de un Controller o un Node: usa getScene() / sceneProperty().
 * - Si es algo â€œde appâ€ (tema, navegaciÃ³n, atajos globales): centralÃ­zalo (Navigator/AppContext).
 */



public class DrawingStuffExample implements IRawExample {
    @Override
    public String name() {
        return "DrawingStuffExample";
    }


    @Override
    public Parent view() {
        Pane root = new Pane();

        // Colores (para que no estÃ©s repitiendo strings)
        final String COLOR_NORMAL = "-fx-background-color: rgb(40, 40, 40);";
        final String COLOR_ALERT   = "-fx-background-color: red;";
        final String COLOR_PRESS   = "-fx-background-color: gold;";   // mientras mantienes clic
        final String COLOR_RELEASE = "-fx-background-color: rgb(84, 98, 124);"; // al soltar
        root.setStyle("-fx-background-color: rgb(40, 40, 40);");

        //text
        final String FONT_PATH = "/Fonts/BBH_Hegarty/BBHHegarty-Regular.ttf";
        final double FONT_SIZE = 50.0;
        Font font = FontUtils.loadOrFallback(
                FONT_PATH,
                FONT_SIZE,
                "Verdana",
                true // debug
        );

        Text text = new Text("WHOOOOOOA!!");
        text.setX(50);
        text.setY(80);
        text.setFont(font);
        //text.setStyle("-fx-fill: white;");
        text.setFill(Color.WHITE);
        root.getChildren().add(text);
        //BUTTON
        Button btn = new Button("Mostrar mensaje");
        btn.setLayoutX(50);
        btn.setLayoutY(100);

        btn.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Mensaje");
            alert.setHeaderText(null);
            alert.setContentText("Hola ðŸ‘‹");

            // Apenas se dispara la acciÃ³n: rojo
            root.setStyle(COLOR_ALERT);

            // Cuando cierras la alerta (Aceptar): vuelve a un color â€œnormalâ€
            alert.setOnHidden(ev -> root.setStyle(COLOR_NORMAL));

            alert.showAndWait();
        });

        //LINE
        Line line = new Line();
        line.setStartX(300);
        line.setStartY(300);
        line.setEndX(140);
        line.setEndY(400);
        line.setStrokeWidth(7);
        line.setStroke(Color.BISQUE);
        line.setOpacity(0.8); //0 invisible, 1 visible full
        line.setRotate(70);


        //RECTANGLE
        Rectangle rectangle = new Rectangle();
        rectangle.setX(400);
        rectangle.setY(400);
        rectangle.setWidth(100);
        rectangle.setHeight(50);
        rectangle.setFill(Color.GOLD);
        rectangle.setStrokeWidth(6);
        rectangle.setStroke(Color.DARKORANGE);
        rectangle.setRotate(20);
        //POLIGON
        Polygon triangle = new Polygon();
        triangle.getPoints().setAll(
                200.0,200.0,
                300.0,300.0,
                200.0,300.0
        );
        triangle.setFill(Color.HOTPINK);
        triangle.setStroke(Color.LIGHTSKYBLUE);
        triangle.setStrokeWidth(6);

        Circle circle = new Circle();
        circle.setCenterX(400);
        circle.setCenterY(100);
        circle.setRadius(20);
        circle.setFill(Color.HOTPINK);
        circle.setStroke(Color.LIGHTSKYBLUE);
        circle.setStrokeWidth(6);

        //IMAGE
        Image image = new Image("Images/logo.png");
        ImageView imageview = new ImageView(image);
        imageview.setX(100);
        imageview.setY(400);
        imageview.setPreserveRatio(true);
        imageview.setFitWidth(150);
        root.getChildren().addAll(line,rectangle,triangle,circle,imageview);
        //ROOT DEMOSTRATION

        root.getChildren().add(btn);

        // Click en la â€œventanaâ€ (en realidad: en el root):
        root.setOnMousePressed(e -> root.setStyle(COLOR_PRESS));      // mientras mantienes clic
        root.setOnMouseReleased(e -> root.setStyle(COLOR_RELEASE));   // al soltar vuelve/cambia
        return root;
    }



}

