package org.roque.orm.bean;

import org.hibernate.Session;
import org.roque.AppLogging;
import org.roque.orm.HibernateUtil;

/**
 * Demo mínima para evidenciar el uso del componente de conexión basado en JavaBean.
 * <p>
 * Esta clase obtiene el {@link DbConnectionBean} desde {@link HibernateUtil}, muestra sus
 * propiedades principales y abre una sesión para validar que la conexión ORM está operativa.
 */
public class BeanConnectionDemo {

    /**
     * Punto de entrada de la demostración.
     *
     * @param args no se utilizan.
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
