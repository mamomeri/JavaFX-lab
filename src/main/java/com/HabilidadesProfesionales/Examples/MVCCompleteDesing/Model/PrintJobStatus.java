package com.HabilidadesProfesionales.Examples.MVCCompleteDesing.Model;

/**
 * Estados posibles de un trabajo de impresiÃ³n.
 * Importante: esto define el "lenguaje" del modelo (su mÃ¡quina de estados).
 */
public enum PrintJobStatus {
    QUEUED,     // En cola, esperando turno
    PRINTING,   // Actualmente imprimiÃ©ndose
    PAUSED,     // Pausado (si estaba activo)
    DONE,       // Terminado con Ã©xito
    CANCELED,   // Cancelado por el usuario
    ERROR       // FallÃ³ (si quieres simular errores)
}

