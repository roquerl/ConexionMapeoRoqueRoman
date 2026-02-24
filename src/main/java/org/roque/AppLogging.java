package org.roque;

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Configura niveles de logging para que la salida de consola sea más limpia durante la práctica.
 */
public final class AppLogging {

    /** Bandera para aplicar configuración una única vez por proceso. */
    private static boolean initialized;

    /** Constructor privado para clase utilitaria estática. */
    private AppLogging() {
    }

    /**
     * Aplica configuración de logs para SLF4J Simple y JUL.
     */
    public static synchronized void configure() {
        if (initialized) {
            return;
        }

        System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "error");
        System.setProperty("org.slf4j.simpleLogger.log.org.mongodb.driver", "error");
        System.setProperty("org.slf4j.simpleLogger.log.org.hibernate", "error");

        Logger root = Logger.getLogger("");
        root.setLevel(Level.SEVERE);
        for (Handler handler : root.getHandlers()) {
            handler.setLevel(Level.SEVERE);
        }

        Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);

        initialized = true;
    }
}
