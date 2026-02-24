package org.roque;

import org.junit.jupiter.api.Test;
import org.roque.orm.db.FuncionesDB;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Pruebas unitarias para utilidades SQL definidas en {@link FuncionesDB}.
 */
class FuncionesDBTest {

    /**
     * Verifica que la función de IVA aplique correctamente el 21%.
     */
    @Test
    void calcularIvaDebeAplicar21Porciento() {
        assertEquals(121.0, FuncionesDB.calcularIva(100.0), 0.0001);
    }
}
