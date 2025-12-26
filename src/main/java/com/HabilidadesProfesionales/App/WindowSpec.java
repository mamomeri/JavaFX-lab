package com.HabilidadesProfesionales.App;

public class WindowSpec {

    public final double width;
    public final double height;
    public final boolean resizable;
    public final boolean fullscreen;
    public final boolean maximized;

    /* =====================================================
       CONSTRUCTOR PRIVADO (control total)
       ===================================================== */
    private WindowSpec(
            double width,
            double height,
            boolean resizable,
            boolean fullscreen,
            boolean maximized
    ) {
        this.width = width;
        this.height = height;
        this.resizable = resizable;
        this.fullscreen = fullscreen;
        this.maximized = maximized;
    }

    /* =====================================================
       FACTORY METHODS â€” CUSTOM
       ===================================================== */

    // custom, resizable por defecto
    public static WindowSpec of(double width, double height) {
        return new WindowSpec(width, height, true, false, false);
    }

    // custom con control de resizable
    public static WindowSpec of(double width, double height, boolean resizable) {
        return new WindowSpec(width, height, resizable, false, false);
    }

    // tamaÃ±o fijo
    public static WindowSpec fixed(double width, double height) {
        return new WindowSpec(width, height, false, false, false);
    }

    /* =====================================================
       MODOS DE VENTANA
       ===================================================== */

    // pantalla completa real
    public static WindowSpec fullscreen() {
        return new WindowSpec(0, 0, false, true, false);
    }

    // maximizada (no fullscreen)
    public static WindowSpec maximized() {
        return new WindowSpec(0, 0, true, false, true);
    }

    /* =====================================================
       PRESETS DE LABORATORIO
       ===================================================== */

    public static final WindowSpec SMALL  = of(400, 300);
    public static final WindowSpec MEDIUM = of(600, 400);
    public static final WindowSpec LARGE  = of(800, 600);
}

