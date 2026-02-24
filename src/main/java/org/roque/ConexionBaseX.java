package org.roque;

import org.basex.core.Context;
import org.basex.core.cmd.CreateDB;
import org.basex.query.QueryProcessor;

import java.io.InputStream;

public class ConexionBaseX {

    public static void main(String[] args) throws Exception {

        Context context = new Context();

        // Cargar XML desde resources correctamente
        InputStream xmlStream =
                ConexionBaseX.class.getResourceAsStream("/biblioteca.xml");

        if (xmlStream == null) {
            System.out.println("No se encontró biblioteca.xml en resources");
            return;
        }

        // Aquí NO usamos toString()
        new CreateDB("Biblioteca", xmlStream).execute(context);

        String query = "for $l in //libro return $l/titulo";

        try (QueryProcessor processor =
                     new QueryProcessor(query, context)) {

            System.out.println(processor.value());
        }

        context.close();
    }
}