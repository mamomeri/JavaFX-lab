package com.HabilidadesProfesionales.App;

import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCombination;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Objects;

/*
 ============================================================================
  NAVIGATOR
  ----------------------------------------------------------------------------
  Este archivo representa una arquitectura clásica y probada en aplicaciones
  gráficas: un "orquestador" central que controla:

   - El ciclo de vida del Stage (ventana)
   - La creación y reemplazo de Scene
   - El manejo de input global (teclado)
   - Atajos de sistema (debug, salir, fullscreen)
   - Reglas de ventana (tamaño, fullscreen, maximized)

  Esta idea NO es nueva:
   - Se ve en motores de videojuegos (Game Loop + Input Manager)
   - Se ve en frameworks UI (Navigation Controller, Router)
   - Se ve incluso en sistemas antiguos (main loop + event dispatch)

  El objetivo del Navigator NO es hacer lógica de negocio,
  sino COORDINAR el sistema gráfico.

  👉 Controllers: saben QUÉ hacer
  👉 Navigator: sabe CUÁNDO y DÓNDE escuchar y mostrar

  Es una arquitectura vieja, simple, estable y muy confiable.
 ============================================================================
*/
public final class Navigator {

    /* =====================================================
       ESTADO GLOBAL CONTROLADO
       ===================================================== */

    // Stage único de la aplicación (ventana principal)
    private static Stage stage;

    // Scene actualmente activa
    private static Scene currentScene;

    /*
     =====================================================
     CONTROLLER DE INPUT INCRUSTADO
     -----------------------------------------------------
     Este controller NO es un controller MVC clásico.
     Es un "input handler" centralizado.

     ¿Por qué está aquí?
     - Porque el input es transversal a toda la app
     - Porque no pertenece a un View ni a un Model
     - Porque así evitamos repetir listeners en cada ejemplo

     Esta técnica es muy común en engines y frameworks.
     =====================================================
    */
    private static final class InputController {

        void onKeyPressed(KeyCode code) {
            switch (code) {
                case W -> moveUp();
                case S -> moveDown();
                case A -> moveLeft();
                case D -> moveRight();
                default -> {
                    // teclas que no nos interesan
                }
            }
        }

        // Acciones base (ejemplo didáctico)
        void moveUp()    { System.out.println("MOVIN' UP!"); }
        void moveDown()  { System.out.println("MOVIN' DOWN!"); }
        void moveLeft()  { System.out.println("MOVIN' LEFT!"); }
        void moveRight() { System.out.println("MOVIN' RIGHT!"); }
    }

    // Instancia única del input controller
    private static final InputController input = new InputController();

    /* =====================================================
       CONSTRUCTOR PRIVADO
       ===================================================== */
    private Navigator() {
        // No se instancia. Es un utility/orchestrator.
    }

    /* =====================================================
       INICIALIZACIÓN
       ===================================================== */

    /**
     * Se llama UNA sola vez desde MainApp.start(...)
     * Establece el Stage principal que el Navigator controlará.
     */
    public static void init(Stage primaryStage) {
        stage = Objects.requireNonNull(primaryStage, "Stage no puede ser null");
    }

    private static void ensureInit() {
        if (stage == null)
            throw new IllegalStateException("Navigator.init(stage) no fue llamado.");
    }

    /* =====================================================
       API PÚBLICA: SHOW
       ===================================================== */

    /**
     * Muestra una vista con configuración por defecto.
     */
    public static void show(Parent view) {
        show(view, WindowSpec.MEDIUM);
    }

    /**
     * Método central de navegación.
     *
     * TODAS las escenas pasan por aquí.
     * Esto garantiza:
     *  - Una sola Scene activa
     *  - Input y debug siempre consistentes
     *  - Estados de ventana controlados
     */
    public static void show(Parent view, WindowSpec spec) {
        ensureInit();
        Objects.requireNonNull(view, "view no puede ser null");
        if (spec == null) spec = WindowSpec.SMALL;

        // Reset de estado para evitar "pegados" raros
        stage.setFullScreen(false);
        stage.setMaximized(false);
        stage.setResizable(spec.resizable);

        // Crear Scene (UN SOLO LUGAR en toda la app)
        currentScene = (spec.fullscreen || spec.maximized)
                ? new Scene(view)
                : new Scene(view, spec.width, spec.height);

        stage.setScene(currentScene);

        // Handlers que SIEMPRE deben existir
        installGlobalHandlers(currentScene);

        // Input base (WASD)
        installInputHandlers(currentScene);

        // Aplicar modo de ventana
        if (spec.fullscreen) stage.setFullScreen(true);
        if (spec.maximized)  stage.setMaximized(true);
    }

    /* =====================================================
       HANDLERS GLOBALES (SISTEMA)
       ===================================================== */

    /**
     * Estos handlers NO dependen del ejemplo.
     * Son comportamiento del sistema.
     */
    private static void installGlobalHandlers(Scene scene) {

        // ESC: salir o salir de fullscreen
        scene.addEventFilter(KeyEvent.KEY_PRESSED, e -> {
            if (e.getCode() == KeyCode.ESCAPE) {
                if (stage.isFullScreen()) {
                    stage.setFullScreen(false);
                } else {
                    stage.close();
                }
                e.consume();
            }
        });

        // F11: toggle fullscreen
        scene.addEventFilter(KeyEvent.KEY_PRESSED, e -> {
            if (e.getCode() == KeyCode.F11) {
                stage.setFullScreen(!stage.isFullScreen());
                e.consume();
            }
        });

        // Ctrl + Q: cerrar aplicación
        scene.getAccelerators().put(
                KeyCombination.keyCombination("CTRL+Q"),
                stage::close
        );

        // Ctrl + I: debug rápido (estado del sistema)
        scene.getAccelerators().put(
                KeyCombination.keyCombination("CTRL+I"),
                () -> {
                    System.out.println("=== Navigator Debug ===");
                    System.out.println("Size: " + stage.getWidth() + " x " + stage.getHeight());
                    System.out.println("Resizable: " + stage.isResizable());
                    System.out.println("Maximized: " + stage.isMaximized());
                    System.out.println("Fullscreen: " + stage.isFullScreen());
                    System.out.println("=======================");
                }
        );
    }

    /* =====================================================
       INPUT BASE (EJEMPLO)
       ===================================================== */

    /**
     * Input común para todos los ejemplos.
     * No conoce Views, no conoce Models.
     * Solo traduce teclas a acciones.
     */
    private static void installInputHandlers(Scene scene) {
        scene.setOnKeyPressed(e -> input.onKeyPressed(e.getCode()));
    }

    /* =====================================================
       UTILIDADES
       ===================================================== */

    /**
     * Cambiar color de fondo de la Scene actual.
     * Útil para ejemplos de dibujo o gráficos.
     */
    public static void setSceneFill(Color color) {
        if (currentScene != null) {
            currentScene.setFill(color);
        }
    }

    /**
     * Ejecutar código en el FX Thread.
     */
    public static void runLater(Runnable r) {
        Platform.runLater(r);
    }
}
