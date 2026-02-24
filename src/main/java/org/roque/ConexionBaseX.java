package org.roque;

import org.basex.core.Context;
import org.basex.core.cmd.CreateDB;
import org.basex.query.QueryProcessor;

import java.io.InputStream;

/**
 * A) Programa 1: conexión a BaseX y ejecución XPath/XQuery.
 */
public class ConexionBaseX {

    public static void main(String[] args) throws Exception {
        String consulta = args.length > 0
                ? args[0]
                : "for $l in //libro where xs:decimal($l/precio) > 30 return $l/titulo/text()";

        Context context = new Context();
        try {
            try (InputStream xmlStream = ConexionBaseX.class.getResourceAsStream("/biblioteca.xml")) {
                if (xmlStream == null) {
                    throw new IllegalStateException("No se encontró biblioteca.xml en src/main/resources");
                }

                new CreateDB("Biblioteca", xmlStream).execute(context);
            }

            QueryProcessor processor = new QueryProcessor(consulta, context);
            try {
                System.out.println("=== Resultado BaseX ===");
                System.out.println("Consulta: " + consulta);
                System.out.println(processor.value());
            } finally {
                processor.close();
            }
        } finally {
            context.close();
        }
    }
}
