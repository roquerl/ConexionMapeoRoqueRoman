package org.roque.orm.bean;

import org.hibernate.Session;
import org.roque.AppLogging;
import org.roque.orm.HibernateUtil;

/**
 * Demo ejecutable para validar que el bean de conexión ORM funciona correctamente.
 */
public class BeanConnectionDemo {

    /**
     * Muestra la configuración del bean y prueba la conexión con {@code SELECT 1}.
     *
     * @param args argumentos de línea de comandos (no se usan).
     */
    public static void main(String[] args) {
        AppLogging.configure();

        DbConnectionBean bean = HibernateUtil.getConnectionBean();
        System.out.println("=== Demo Bean de Conexión ORM ===");
        System.out.println("Driver: " + bean.getDriverClass());
        System.out.println("URL JDBC: " + bean.getJdbcUrl());
        System.out.println("Usuario: " + bean.getUsername());

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Number result = (Number) session.createNativeQuery("SELECT 1").getSingleResult();
            System.out.println("Conexión OK (SELECT 1) => " + result);
        }
    }
}
