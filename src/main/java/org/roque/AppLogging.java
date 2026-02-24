package org.roque;

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Configuración mínima para reducir ruido de logs en consola durante la práctica.
 */
public final class AppLogging {

    private static boolean initialized;

    private AppLogging() {
    }

    public static synchronized void configure() {
        if (initialized) {
            return;
        }

        // Logs SLF4J Simple (usados por Mongo driver y parte de Hibernate/JBoss Logging).
        System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "warn");
        System.setProperty("org.slf4j.simpleLogger.log.org.mongodb.driver", "error");
        System.setProperty("org.slf4j.simpleLogger.log.org.hibernate", "warn");

        // Logs JUL (en algunos entornos Hibernate/JBoss pueden enrutar por java.util.logging).
        Logger root = Logger.getLogger("");
        root.setLevel(Level.WARNING);
        for (Handler handler : root.getHandlers()) {
            handler.setLevel(Level.WARNING);
        }

        Logger.getLogger("org.hibernate").setLevel(Level.WARNING);

        initialized = true;
    }
}
