package org.roque.orm;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.roque.orm.entity.Autor;
import org.roque.orm.entity.Libro;
import org.roque.orm.entity.Prestamo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Programa de la parte B: ORM, HQL y caso de uso completo.
 */
public class OrmPracticaApp {

    /**
     * Ejecuta toda la parte ORM/HQL.
     *
     * @param args argumentos de línea de comandos (no se usan).
     */
    public static void main(String[] args) {
        insertarDatosIniciales();

        System.out.println("\n========== HQL (3 ejemplos) ==========");
        ejemploHqlJoin();
        ejemploHqlJoinDosTablas();
        ejemploHqlSubconsulta();

        System.out.println("\n========== CASO DE USO ==========");
        seleccionesSimplesYJoins();
        sentenciasDml();
        procedimientoYFuncion();

        HibernateUtil.getSessionFactory().close();
    }

    /**
     * Inserta datos de ejemplo iniciales para autores, libros y préstamos.
     */
    private static void insertarDatosIniciales() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        Autor martin = new Autor("Robert C. Martin");
        Autor bloch = new Autor("Joshua Bloch");

        Libro cleanCode = new Libro("Clean Code", new BigDecimal("35.00"));
        Libro effectiveJava = new Libro("Effective Java", new BigDecimal("40.00"));

        martin.addLibro(cleanCode);
        bloch.addLibro(effectiveJava);

        cleanCode.addPrestamo(new Prestamo("Ana", LocalDate.now().minusDays(2)));
        effectiveJava.addPrestamo(new Prestamo("Luis", LocalDate.now().minusDays(1)));

        session.persist(martin);
        session.persist(bloch);

        tx.commit();
        session.close();
    }

    /**
     * HQL 1: join simple entre Libro y Autor.
     */
    private static void ejemploHqlJoin() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<Object[]> rows = session.createQuery(
                            "select l.titulo, a.nombre from Libro l join l.autor a order by l.titulo",
                            Object[].class)
                    .list();
            rows.forEach(r -> System.out.println("Libro=" + r[0] + " | Autor=" + r[1]));
        }
    }

    /**
     * HQL 2: join entre tres tablas (Prestamo, Libro y Autor).
     */
    private static void ejemploHqlJoinDosTablas() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<Object[]> rows = session.createQuery(
                            "select l.titulo, a.nombre, p.socio from Prestamo p join p.libro l join l.autor a",
                            Object[].class)
                    .list();
            rows.forEach(r -> System.out.println("Prestamo -> " + r[2] + " tiene '" + r[0] + "' de " + r[1]));
        }
    }

    /**
     * HQL 3: subconsulta para libros por encima del precio medio.
     */
    private static void ejemploHqlSubconsulta() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<Libro> caros = session.createQuery(
                            "from Libro l where l.precio > (select avg(l2.precio) from Libro l2)",
                            Libro.class)
                    .list();
            caros.forEach(l -> System.out.println("Por encima de la media: " + l.getTitulo() + " -> " + l.getPrecio()));
        }
    }

    /**
     * Caso de uso: selección simple y join básico.
     */
    private static void seleccionesSimplesYJoins() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<Libro> libros = session.createQuery("from Libro", Libro.class).list();
            System.out.println("Seleccion simple (tabla libros): " + libros.size() + " filas");

            List<Object[]> join = session.createQuery(
                            "select l.titulo, a.nombre from Libro l join l.autor a",
                            Object[].class)
                    .list();
            System.out.println("Join Libro-Autor: " + join.size() + " filas");
        }
    }

    /**
     * Caso de uso DML: inserción, actualización y borrado.
     */
    private static void sentenciasDml() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();

            Autor nuevoAutor = new Autor("Martin Fowler");
            Libro refactoring = new Libro("Refactoring", new BigDecimal("45.00"));
            nuevoAutor.addLibro(refactoring);
            session.persist(nuevoAutor);

            int updated = session.createMutationQuery(
                            "update Libro l set l.precio = l.precio + 5 where l.titulo = :titulo")
                    .setParameter("titulo", "Clean Code")
                    .executeUpdate();

            int deleted = session.createMutationQuery(
                            "delete Prestamo p where p.socio = :socio")
                    .setParameter("socio", "Luis")
                    .executeUpdate();

            tx.commit();
            System.out.println("DML -> insert(1 autor+1 libro), update=" + updated + ", delete=" + deleted);
        }
    }

    /**
     * Caso de uso SQL nativo: crea aliases H2, ejecuta función y procedimiento.
     */
    private static void procedimientoYFuncion() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();

            session.createNativeMutationQuery(
                    "CREATE ALIAS IF NOT EXISTS CALCULAR_IVA FOR 'org.roque.orm.db.FuncionesDB.calcularIva'")
                    .executeUpdate();

            session.createNativeMutationQuery(
                    "CREATE ALIAS IF NOT EXISTS SUBIR_PRECIO FOR 'org.roque.orm.db.FuncionesDB.subirPrecio'")
                    .executeUpdate();

            Double pvpConIva = ((Number) session.createNativeQuery("SELECT CALCULAR_IVA(40.0)")
                    .getSingleResult()).doubleValue();

            session.createNativeMutationQuery("CALL SUBIR_PRECIO(10.0)").executeUpdate();

            tx.commit();

            System.out.println("Función CALCULAR_IVA(40) = " + pvpConIva);
            System.out.println("Procedimiento SUBIR_PRECIO(10%) ejecutado correctamente.");
        }
    }
}
