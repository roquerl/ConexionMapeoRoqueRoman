package org.roque.orm.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Funciones auxiliares para registrar aliases SQL en H2 desde Hibernate.
 * <p>
 * Se usan como función y procedimiento almacenado dentro del caso de uso de la práctica.
 */
public final class FuncionesDB {

    /** Constructor privado para clase utilitaria estática. */
    private FuncionesDB() {
    }

    /**
     * Función SQL (H2 ALIAS): calcula el precio con IVA (21%).
     *
     * @param base precio base sin IVA.
     * @return precio base con IVA aplicado.
     */
    public static double calcularIva(double base) {
        return base * 1.21;
    }

    /**
     * Procedimiento SQL (H2 ALIAS): incrementa el precio de todos los libros por porcentaje.
     *
     * @param conn conexión JDBC proporcionada por H2 al invocar el alias.
     * @param porcentaje porcentaje de incremento (por ejemplo, 10 para +10%).
     * @throws SQLException si ocurre un error de ejecución SQL.
     */
    public static void subirPrecio(Connection conn, double porcentaje) throws SQLException {
        String sql = "UPDATE libros SET precio = precio * (1 + (? / 100))";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, porcentaje);
            ps.executeUpdate();
        }
    }
}
