package org.roque;

import org.junit.jupiter.api.Test;
import org.roque.orm.db.FuncionesDB;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FuncionesDBTest {

    @Test
    void calcularIvaDebeAplicar21Porciento() {
        assertEquals(121.0, FuncionesDB.calcularIva(100.0), 0.0001);
    }
}
