package com.HabilidadesProfesionales.Utils;

import javafx.application.Platform;
import javafx.scene.control.Labeled;
import javafx.scene.text.Font;

import java.io.InputStream;

public final class FontUtils {

    private FontUtils() {}

    /** Carga una fuente desde resources (classpath). Si falla, usa fallbackFamily. */
    public static Font loadOrFallback(String resourcePath,
                                      double size,
                                      String fallbackFamily,
                                      boolean debug) {

        if (resourcePath == null || resourcePath.isBlank()) {
            if (debug) System.out.println("[FontUtils] resourcePath vacÃ­o -> fallback");
            return Font.font(fallbackFamily, size);
        }

        try (InputStream is = FontUtils.class.getResourceAsStream(resourcePath)) {
            if (is == null) {
                if (debug) System.out.println("[FontUtils] No encuentro " + resourcePath + " -> fallback");
                return Font.font(fallbackFamily, size);
            }

            Font f = Font.loadFont(is, size);
            if (f == null) {
                if (debug) System.out.println("[FontUtils] loadFont devolviÃ³ null (" + resourcePath + ") -> fallback");
                return Font.font(fallbackFamily, size);
            }

            if (debug) {
                System.out.println("[FontUtils] OK -> name=" + f.getName()
                        + " | family=" + f.getFamily()
                        + " | size=" + f.getSize());
            }
            return f;

        } catch (Exception ex) {
            if (debug) System.out.println("[FontUtils] ERROR: " + ex.getMessage() + " -> fallback");
            return Font.font(fallbackFamily, size);
        }
    }

    /**
     * Aplica la fuente a un Labeled (Label/Button/etc).
     * Lo hace en FX thread para evitar â€œpisadasâ€ de CSS/layout.
     */
    public static void applyTo(Labeled node,
                               String resourcePath,
                               double size,
                               String fallbackFamily,
                               boolean debug) {

        if (node == null) return;

        Font font = loadOrFallback(resourcePath, size, fallbackFamily, debug);

        Runnable apply = () -> node.setFont(font);

        if (Platform.isFxApplicationThread()) apply.run();
        else Platform.runLater(apply);
    }
}

