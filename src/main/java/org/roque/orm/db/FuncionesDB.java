package org.roque.orm.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public final class FuncionesDB {

    private FuncionesDB() {
    }

    // Función SQL (H2 ALIAS): devuelve precio con IVA.
    public static double calcularIva(double base) {
        return base * 1.21;
    }

    // Procedimiento SQL (H2 ALIAS): sube precio de todos los libros por porcentaje.
    public static void subirPrecio(Connection conn, double porcentaje) throws SQLException {
        String sql = "UPDATE libros SET precio = precio * (1 + (? / 100))";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, porcentaje);
            ps.executeUpdate();
        }
    }
}
