# Documento de referencia – Simulador de Cola de Impresión (Spooler) en JavaFX

## 1. Propósito del ejemplo
Este ejemplo implementa una **aplicación de escritorio (desktop)** en **JavaFX**, orientada a eventos, que simula un **spooler de impresión**: recibe trabajos de impresión, los **encola**, gestiona un **trabajo activo**, permite **pausar/reanudar**, **cancelar**, y muestra **progreso** y **estadísticas**.

El objetivo didáctico es doble:
- Ilustrar una arquitectura **MVC (Model–View–Controller)** con **separación formal de responsabilidades**.
- Practicar un flujo realista de UI: **tabla con actualización automática**, controles de operación y **dos pantallas intercambiables**.

---

## 2. ¿Qué es un spooler?
Un **spooler** (cola de impresión) es un componente de software que administra trabajos enviados a un dispositivo de salida (por ejemplo, una impresora). En lugar de imprimir “directo”, el sistema:
- **Recibe** solicitudes (jobs).
- **Las encola** (queue).
- **Decide orden de ejecución** (por llegada o por prioridad).
- Ejecuta **un trabajo activo** a la vez, reportando estado y progreso.

### Nota histórica breve
El término **SPOOL** se asocia clásicamente a *Simultaneous Peripheral Operations Online*: la idea de **desacoplar** el envío de tareas del procesamiento físico, para que el usuario/cliente no tenga que esperar a que el dispositivo termine. Este patrón se reutiliza hoy en:
- Colas de tareas en servidores.
- Planificadores de trabajos (*job scheduling*).
- Sistemas de producción con priorización y monitoreo.

---

## 3. Tipo de aplicación y contexto de uso
### 3.1 Tipo de aplicación (computacional)
- **Aplicación GUI** (Graphical User Interface) orientada a eventos.
- **Modelo reactivo**: la UI refleja cambios del modelo mediante **properties** y **bindings** (sin “refresh manual” constante).
- **Simulación temporal**: el progreso avanza con un “tick” (paso de tiempo) configurado por un slider (ms/página).

### 3.2 Contexto típico
La estructura es la misma que se encuentra en:
- Herramientas administrativas (inventarios, turnos, dashboards).
- Monitores de colas (impresión, tareas batch, procesamiento de archivos).
- Simuladores de laboratorio para practicar estructura de datos y concurrencia.

---

## 4. Arquitectura aplicada: MVC + navegación interna
La arquitectura se organiza en **capas** con responsabilidades estrictas.

### 4.1 Principio clave: separación de responsabilidades
- **Model**: dominio y estado (cola, job activo, reglas, estadísticas). No crea UI ni conoce `Stage`.
- **View**: carga el FXML y entrega un `Parent` listo para usarse. Es “fábrica” de UI.
- **Controller**: coordina eventos de UI, realiza bindings, invoca acciones del modelo.
- **App / Infra**: punto de entrada del laboratorio y utilidades comunes (navegación, tamaños de ventana).

---

## 5. Estructura de paquetes y rol de cada parte
A continuación se describe el rol típico de cada paquete/clase.

### 5.1 `com.HabilidadesProfesionales.App`
**Responsabilidad:** infraestructura del laboratorio.

- `MainApp` (JavaFX `Application`):
  - Inicializa JavaFX.
  - Selecciona qué ejemplo ejecutar.
  - Usa `Navigator` para mostrar la vista de un ejemplo.

- `Navigator`:
  - Encapsula el `Stage` principal.
  - Permite `show(Parent, WindowSpec)`.
  - Evita que controladores creen `Stage`/`Scene`.

- `WindowSpec`:
  - Define tamaño y modo de ventana (pequeña/mediana/grande, resizable, maximized, fullscreen).

- `IRawExample`:
  - Contrato para ejemplos del laboratorio: típicamente `name()` y `view()`.

### 5.2 `com.HabilidadesProfesionales.Examples.MVCCompleteDesing`
**Responsabilidad:** punto de entrada del ejemplo (composición de MVC).

- `MVCCompleteDesignExample`:
  - Crea el **modelo** (`SpoolerModel`).
  - Instancia **vistas** (`MainView`, `SecondaryView`) y carga FXML.
  - Inyecta el modelo a sus controladores.
  - Configura navegación interna: cambiar root entre `mainRoot` y `secondaryRoot`.
  - Aplica CSS **solo** a este ejemplo (aislamiento de estilos).

### 5.3 `...MVCCompleteDesing.Model`
**Responsabilidad:** dominio (spooler).

- `PrintJob`:
  - Entidad del dominio: `title`, `pages`, `priority`, `status`, `progressPages` (o equivalente).
  - Idealmente expone **JavaFX properties** (`StringProperty`, `IntegerProperty`, `ObjectProperty`, etc.) para binding.

- `PrintJobStatus`:
  - Enum de estados (ejemplo): `QUEUED`, `PRINTING`, `PAUSED`, `DONE`, `CANCELED`.
  - Permite tratar el flujo como **máquina de estados**.

- `SpoolerModel`:
  - Mantiene la **cola** de trabajos y el **job activo**.
  - Ejecuta la simulación: avanza páginas, cambia estados, registra historial.
  - Expone métricas: completados, cancelados, promedio ms/página, etc.

### 5.4 `...MVCCompleteDesing.View`
**Responsabilidad:** cargar FXML, no contener lógica de negocio.

- `MainView`:
  - Carga `MainView.fxml` con `FXMLLoader`.
  - Guarda referencia al controlador instanciado por FXML.
  - Expone `Parent load()` y `MainController getController()`.

- `SecondaryView`:
  - Carga `SecondaryView.fxml`.
  - Expone `Parent load()` y `SecondaryController getController()`.

**Por qué existe la capa View:**
- Evita que el resto del sistema “sepa” rutas de FXML.
- Centraliza manejo de errores al cargar.
- Mantiene un patrón consistente con el laboratorio.

### 5.5 `...MVCCompleteDesing.Controller`
**Responsabilidad:** UI + eventos + bindings.

- `MainController`:
  - Conecta botones a acciones del modelo.
  - Configura columnas de tabla (cellValueFactory).
  - Usa bindings para mostrar job activo y progreso.
  - Publica un callback para navegación (“ir a stats”).

- `SecondaryController`:
  - Muestra estadísticas e historial.
  - Permite volver a la pantalla principal.

---

## 6. Navegación entre dos pantallas (intercambio de root)
En este ejemplo, la navegación se logra cambiando el **root de la Scene**.

**Idea:**
- Se carga `mainRoot` y `secondaryRoot` una sola vez.
- Para cambiar de pantalla:
  - `mainRoot.getScene().setRoot(secondaryRoot)`
  - `secondaryRoot.getScene().setRoot(mainRoot)`

**Ventajas:**
- No se recrea el Stage ni se pierde configuración.
- Las vistas ya están cargadas; el intercambio es rápido.

---

## 7. Conceptos de computación teórica y estructuras de datos
### 7.1 Cola y/o cola con prioridad
Un spooler se modela naturalmente con una **cola**.
- Si se atiende por orden de llegada: **FIFO**.
- Si se atiende por prioridad: **Priority Queue** (típicamente implementada con un heap).

### 7.2 Máquina de estados
El `PrintJobStatus` define un conjunto finito de estados y transiciones válidas. Esto formaliza el comportamiento:
- `QUEUED → PRINTING`
- `PRINTING ↔ PAUSED`
- `PRINTING → DONE`
- `QUEUED/PRINTING/PAUSED → CANCELED`

### 7.3 Modelo observable
Para que la tabla se actualice sin “refresh manual”, el modelo puede exponer:
- `ObservableList<PrintJob>` para la cola.
- `ObjectProperty<PrintJob>` para el activo.
- Properties de progreso y estado por cada trabajo.

---

## 8. Concurrencia y ejecución en JavaFX
JavaFX opera con el **JavaFX Application Thread**. Regla práctica:
- Cambios a la UI deben ocurrir en ese hilo.

Para simulaciones temporizadas (avance de páginas), hay opciones:
- `Timeline` (muy común en UI): corre “ticks” sin bloquear la interfaz.
- `ScheduledExecutorService` / `Task` (si el trabajo es pesado): y sincronizar a UI con `Platform.runLater`.

**Objetivo:** evitar que el hilo de UI se bloquee (congelamiento de la app).

---

## 9. Diseño de interfaz (UI) y experiencia (UX)
### 9.1 Decisiones UI típicas implementadas
- Jerarquía visual: título, secciones (“Cola”, “Trabajo activo”).
- Tabla como vista principal del estado.
- Botón primario para navegación (“Ver Detalles / Stats”).
- Slider para controlar velocidad (ms/página).

### 9.2 Recomendaciones UX para iterar
- Deshabilitar botones cuando no aplican (sin selección, sin job activo).
- Confirmaciones para cancelar (acciones destructivas).
- Mensajes claros en barra de estado (qué ocurrió y qué se espera).
- Consistencia de texto: verbos en infinitivo o imperativo, pero uniforme.

---

## 10. Estilos y tipografías: CSS aislado y elección de fuentes
### 10.1 Aislamiento del CSS (solo para este ejemplo)
Para que el tema no afecte otros ejemplos del laboratorio:
- Se agrega el stylesheet **solo** a `mainRoot` y `secondaryRoot`.
- Se recomienda además dar una **clase raíz** en el FXML para “encapsular” selectores.

**Recomendación práctica (encapsulación):**
- En el root de cada FXML, añadir algo como `styleClass="mvc-lab-root"`.
- En el CSS, prefijar selectores con `.mvc-lab-root ...`.

Esto evita que el CSS toque componentes de otros ejemplos.

### 10.2 FontUtils vs solo CSS (pros y contras)
**A) Carga por código (FontUtils / `Font.loadFont`)**
- Pros:
  - Control explícito y diagnóstico (logs).
  - Se valida carga y familia disponible.
  - Permite lógica dinámica (cambiar tema/fuente según contexto).
- Contras:
  - Más código y mantenimiento.
  - Riesgo de mezclar presentación con lógica si se usa desde controladores.

**B) Solo CSS**
- Pros:
  - Estética centralizada en un archivo.
  - Más simple de iterar (cambios visuales sin tocar controladores).
  - Refuerza separación de responsabilidades.
- Contras:
  - JavaFX CSS no soporta “variables” como CSS web (propiedades custom tipo `--brand-font` no aplican).
  - Errores de `@font-face`/rutas pueden ser menos evidentes si no se imprime diagnóstico.

**Conclusión práctica recomendada:**
- Cargar fuentes una sola vez desde el *entry point* del ejemplo (si se desea diagnóstico).
- Definir el look & feel en CSS y aplicarlo solo al root del ejemplo.

### 10.3 Consejos UX/UI para escoger fuentes
- Mantener máximo **2 familias**: una para títulos (display) y otra para UI/tabla.
- Para texto y tablas, priorizar sans-serif legible (p. ej. Inter).
- Para títulos, una display (p. ej. Bebas Neue) solo en encabezados.
- Usar pesos (Regular/Medium/SemiBold) para jerarquía, no cambiar familia en cada elemento.
- Probar legibilidad en tamaños pequeños (tabla y labels secundarios).
- Evitar combinaciones muy “agresivas”: si el título es fuerte, el cuerpo debe ser neutro.

---

## 11. Checklist rápido de consistencia (errores comunes)
- Cada `@FXML` del controlador tiene su `fx:id` correcto en FXML.
- Las columnas del `TableView` están declaradas con el tipo real (ej. `TableColumn<PrintJob, PrintJobStatus>`).
- La tabla usa una `ObservableList` del modelo o una lista observable en el controller.
- La lógica de simulación no bloquea el hilo de UI.
- El CSS está aislado (stylesheet solo para este ejemplo + clase raíz).

---

## 12. Cierre
Este laboratorio representa un patrón de aplicación típico: **gestión de trabajos en cola**, con UI reactiva y una arquitectura que separa correctamente infraestructura, composición, dominio y presentación. La misma estructura puede reutilizarse para sistemas administrativos y simuladores más complejos sin perder orden ni claridad arquitectónica.
