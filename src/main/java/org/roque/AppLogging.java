package org.roque;

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Configuración mínima de logging para reducir ruido de consola en ejecución local.
 * <p>
 * Se aplica tanto a SLF4J Simple como a JUL para evitar que mensajes informativos del stack
 * (MongoDB/Hibernate) se vean como errores en la consola del IDE.
 */
public final class AppLogging {

    private static boolean initialized;

    private AppLogging() {
    }

    /**
     * Aplica la configuración de logs una sola vez por proceso.
     */
    public static synchronized void configure() {
        if (initialized) {
            return;
        }

        // Logs SLF4J Simple (usados por Mongo driver y parte de Hibernate/JBoss Logging).
        System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "error");
        System.setProperty("org.slf4j.simpleLogger.log.org.mongodb.driver", "error");
        System.setProperty("org.slf4j.simpleLogger.log.org.hibernate", "error");

        // Logs JUL (en algunos entornos Hibernate/JBoss pueden enrutar por java.util.logging).
        Logger root = Logger.getLogger("");
        root.setLevel(Level.SEVERE);
        for (Handler handler : root.getHandlers()) {
            handler.setLevel(Level.SEVERE);
        }

        Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);

        initialized = true;
    }
}
