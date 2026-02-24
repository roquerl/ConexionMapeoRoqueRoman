package org.roque;

import org.basex.core.Context;
import org.basex.core.cmd.CreateDB;
import org.basex.query.QueryProcessor;

import java.net.URL;
import java.nio.file.Paths;

/**
 * A) Programa 1: conexión a BaseX y ejecución XPath/XQuery.
 */
public class ConexionBaseX {

    public static void main(String[] args) throws Exception {
        String consulta = args.length > 0
                ? args[0]
                : "for $l in //libro where xs:decimal($l/precio) > 30 return data($l/titulo)";

        Context context = new Context();
        try {
            URL resource = ConexionBaseX.class.getResource("/biblioteca.xml");
            if (resource == null) {
                throw new IllegalStateException("No se encontró biblioteca.xml en src/main/resources");
            }

            String xmlPath = Paths.get(resource.toURI()).toString();
            new CreateDB("Biblioteca", xmlPath).execute(context);

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
