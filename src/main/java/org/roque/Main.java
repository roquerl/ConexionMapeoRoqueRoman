package org.roque;

import org.roque.orm.OrmPracticaApp;

/**
 * Lanzador general para ejecutar toda la práctica desde una sola clase.
 */
public class Main {
    public static void main(String[] args) throws Exception {
        AppLogging.configure();
        System.out.println("=== A.1 BaseX ===");
        ConexionBaseX.main(new String[]{});

        System.out.println("\n=== A.2 MongoDB ===");
        ConexionMongo.main(new String[]{});

        System.out.println("\n=== B. ORM / HQL / Caso de uso ===");
        OrmPracticaApp.main(new String[]{});
    }
}
