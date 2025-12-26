package com.HabilidadesProfesionales.Examples.BlurModal.Controller;

import com.HabilidadesProfesionales.Examples.BlurModal.Model.ConfirmOverlayModel;
import com.HabilidadesProfesionales.Examples.BlurModal.View.ConfirmOverlayView;
import javafx.animation.*;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.media.AudioClip;
import javafx.util.Duration;

import java.net.URL;

public class ConfirmOverlayController {

    private final StackPane root;
    private final Node contentNode; // lo que se difumina (normalmente tu backgroundLayer)
    private final ConfirmOverlayModel model;
    private final ConfirmOverlayView view;

    // Blur
    private final GaussianBlur blur = new GaussianBlur(0);

    // AnimaciÃ³n config
    private final Duration IN = Duration.millis(180);
    private final Duration OUT = Duration.millis(130);
    private final double DIM_TARGET_OPACITY = 0.55;

    // Animaciones
    private final FadeTransition dimIn;
    private final FadeTransition dimOut;
    private final FadeTransition cardIn;
    private final FadeTransition cardOut;
    private final ScaleTransition popIn;
    private final ScaleTransition popOut;
    private final Timeline blurIn;
    private final Timeline blurOut;

    // Callbacks
    private Runnable onAccept = () -> {};
    private Runnable onCancel = () -> {};

    // Sonido (REAL JavaFX)
    private AudioClip clickSound;

    // âœ… Solo al abrir
    private boolean playSoundOnOpen = true;

    private boolean animating = false;

    // Handler para ESC (se crea en constructor)
    private final EventHandler<KeyEvent> escFilter;

    public ConfirmOverlayController(StackPane root, Node contentNode,
                                    ConfirmOverlayModel model, ConfirmOverlayView view) {
        this.root = root;
        this.contentNode = contentNode;
        this.model = model;
        this.view = view;

        this.escFilter = e -> {
            if (this.model.isShowing() && e.getCode() == KeyCode.ESCAPE) {

                // âœ… NO sonido al cerrar (porque ESC es cerrar)
                // playClick();

                cancel();
                e.consume();
            }
        };

        // Montar overlay encima del root (dim debajo del card)
        root.getChildren().addAll(view.getDim(), view.getCard());

        // Dim cubre todo el root
        view.getDim().widthProperty().bind(root.widthProperty());
        view.getDim().heightProperty().bind(root.heightProperty());

        root.setFocusTraversable(true);

        // ===== Animaciones =====
        dimIn = new FadeTransition(IN, view.getDim());
        dimIn.setFromValue(0);
        dimIn.setToValue(DIM_TARGET_OPACITY);

        dimOut = new FadeTransition(OUT, view.getDim());
        dimOut.setFromValue(DIM_TARGET_OPACITY);
        dimOut.setToValue(0);

        cardIn = new FadeTransition(IN, view.getCard());
        cardIn.setFromValue(0);
        cardIn.setToValue(1);

        cardOut = new FadeTransition(OUT, view.getCard());
        cardOut.setFromValue(1);
        cardOut.setToValue(0);

        popIn = new ScaleTransition(IN, view.getCard());
        popIn.setFromX(0.96);
        popIn.setFromY(0.96);
        popIn.setToX(1.0);
        popIn.setToY(1.0);

        popOut = new ScaleTransition(OUT, view.getCard());
        popOut.setFromX(1.0);
        popOut.setFromY(1.0);
        popOut.setToX(0.96);
        popOut.setToY(0.96);

        blurIn = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(blur.radiusProperty(), 0)),
                new KeyFrame(IN, new KeyValue(blur.radiusProperty(), 16))
        );

        blurOut = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(blur.radiusProperty(), 16)),
                new KeyFrame(OUT, new KeyValue(blur.radiusProperty(), 0))
        );

        wireEvents();
    }

    private void wireEvents() {

        // âœ… SOLO cerrar con botones (aceptar/cancelar)
        view.getAcceptBtn().setOnAction(e -> {
            // âœ… NO sonido al cerrar
            // playClick();

            accept();
        });

        view.getCancelBtn().setOnAction(e -> {
            // âœ… NO sonido al cerrar
            // playClick();

            cancel();
        });

        // âœ… El dim NO cierra el modal. Solo bloquea el fondo.
        view.getDim().setOnMouseClicked(e -> e.consume());
    }

    // ==========================
    // Config pÃºblica
    // ==========================
    public void setPlaySoundOnOpen(boolean value) {
        this.playSoundOnOpen = value;
    }

    // ==========================
    // Sonido (AudioClip JavaFX)
    // ==========================
    public void setClickSound(String classpathResource) {
        if (classpathResource == null || classpathResource.isBlank()) {
            clickSound = null;
            return;
        }

        URL url = getClass().getResource(classpathResource);
        if (url == null) {
            System.out.println("[ConfirmOverlay] No se encontrÃ³ el sonido: " + classpathResource);
            clickSound = null;
            return;
        }

        clickSound = new AudioClip(url.toString());

        // âœ… Warm-up: precarga para que el primer play no tenga delay
        double prevVol = clickSound.getVolume();
        clickSound.setVolume(0.0);
        clickSound.play();
        clickSound.setVolume(prevVol);
    }

    private void playClick() {
        if (clickSound != null) clickSound.play();
    }

    // ==========================
    // API pÃºblica
    // ==========================
    public void show(String title, String message, Runnable onAccept, Runnable onCancel) {
        if (animating || model.isShowing()) return;

        model.setTitle(title != null ? title : "ConfirmaciÃ³n");
        model.setMessage(message != null ? message : "Â¿EstÃ¡s seguro?");

        this.onAccept = (onAccept != null) ? onAccept : () -> {};
        this.onCancel = (onCancel != null) ? onCancel : () -> {};

        open();
    }

    public void hide() {
        if (animating || !model.isShowing()) return;
        close(null);
    }

    public boolean isShowing() {
        return model.isShowing();
    }

    // ==========================
    // Interno
    // ==========================
    private void open() {
        animating = true;
        model.setShowing(true);

        // âœ… SONIDO SOLO AL ABRIR
        if (playSoundOnOpen) {
            playClick();
        }

        // bloquear fondo
        contentNode.setMouseTransparent(true);

        // blur al fondo
        contentNode.setEffect(blur);

        // preparar nodos
        view.getDim().setVisible(true);
        view.getDim().setOpacity(0);

        view.getCard().setVisible(true);
        view.getCard().setOpacity(0);
        view.getCard().setScaleX(0.96);
        view.getCard().setScaleY(0.96);

        // orden visual
        view.getDim().toFront();
        view.getCard().toFront();

        // foco + ESC
        root.requestFocus();
        root.addEventFilter(KeyEvent.KEY_PRESSED, escFilter);

        // animar
        blurIn.playFromStart();
        dimIn.playFromStart();
        new ParallelTransition(cardIn, popIn).playFromStart();

        cardIn.setOnFinished(ev -> animating = false);
    }

    private void close(Runnable afterClose) {
        animating = true;

        // ðŸ”Š SONIDO AL CERRAR (si algÃºn dÃ­a lo quieres)
        // playClick();

        blurOut.playFromStart();
        dimOut.playFromStart();

        ParallelTransition exit = new ParallelTransition(cardOut, popOut);
        exit.setOnFinished(ev -> {

            contentNode.setEffect(null);
            contentNode.setMouseTransparent(false);

            view.getDim().setVisible(false);
            view.getDim().setOpacity(0);

            view.getCard().setVisible(false);
            view.getCard().setOpacity(0);

            model.setShowing(false);
            animating = false;

            root.removeEventFilter(KeyEvent.KEY_PRESSED, escFilter);

            if (afterClose != null) afterClose.run();
        });
        exit.playFromStart();
    }

    private void accept() {
        close(onAccept);
    }

    private void cancel() {
        close(onCancel);
    }
}

