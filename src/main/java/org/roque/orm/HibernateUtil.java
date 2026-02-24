package org.roque.orm;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.roque.orm.bean.DbConnectionBean;
import org.roque.orm.entity.Autor;
import org.roque.orm.entity.Libro;
import org.roque.orm.entity.Prestamo;

import java.util.Properties;

/**
 * Utilidad central para inicializar y exponer el {@link SessionFactory} de Hibernate.
 * <p>
 * La configuración se obtiene desde un {@link DbConnectionBean} reutilizable, de modo que
 * cualquier programa del proyecto pueda consultar o reutilizar la misma configuración de conexión.
 */
public final class HibernateUtil {

    private static final DbConnectionBean CONNECTION_BEAN = buildDefaultConnectionBean();
    private static final SessionFactory SESSION_FACTORY = buildSessionFactory(CONNECTION_BEAN);

    private HibernateUtil() {
    }

    /**
     * @return instancia única de {@link SessionFactory} para toda la aplicación.
     */
    public static SessionFactory getSessionFactory() {
        return SESSION_FACTORY;
    }

    /**
     * @return bean de conexión reutilizable desde cualquier clase/programa.
     */
    public static DbConnectionBean getConnectionBean() {
        return CONNECTION_BEAN;
    }

    /**
     * Construye la configuración por defecto del bean de conexión.
     *
     * @return bean con parámetros iniciales para la práctica.
     */
    private static DbConnectionBean buildDefaultConnectionBean() {
        DbConnectionBean bean = new DbConnectionBean();
        bean.setShowSql(false);
        bean.setFormatSql(false);
        bean.setHighlightSql(false);
        return bean;
    }

    /**
     * Construye el {@link SessionFactory} a partir del bean de conexión recibido.
     *
     * @param connectionBean bean con la configuración JDBC/Hibernate.
     * @return sesión de fábrica lista para crear sesiones ORM.
     */
    private static SessionFactory buildSessionFactory(DbConnectionBean connectionBean) {
        Properties settings = connectionBean.toHibernateProperties();

        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .applySettings(settings)
                .build();

        return new MetadataSources(registry)
                .addAnnotatedClass(Autor.class)
                .addAnnotatedClass(Libro.class)
                .addAnnotatedClass(Prestamo.class)
                .buildMetadata()
                .buildSessionFactory();
    }
}
