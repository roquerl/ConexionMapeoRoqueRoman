package org.roque;

import org.roque.orm.OrmPracticaApp;

/**
 * Punto de entrada principal de la práctica completa.
 * <p>
 * Esta clase ejecuta en secuencia:
 * <ol>
 *   <li>Consulta BaseX (parte A.1).</li>
 *   <li>Consulta MongoDB (parte A.2).</li>
 *   <li>Ejercicios ORM/HQL y caso de uso (parte B).</li>
 * </ol>
 */
public class Main {

    /**
     * Ejecuta toda la práctica de extremo a extremo.
     *
     * @param args argumentos de línea de comandos (no se usan).
     * @throws Exception si alguna de las partes lanza error en tiempo de ejecución.
     */
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
