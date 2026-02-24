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
 * Utilidad central para crear y exponer {@link SessionFactory} de Hibernate.
 * <p>
 * Utiliza un {@link DbConnectionBean} compartido para centralizar la configuración de conexión.
 */
public final class HibernateUtil {

    /** Bean de conexión compartido por toda la aplicación. */
    private static final DbConnectionBean CONNECTION_BEAN = buildDefaultConnectionBean();

    /** Fábrica de sesiones singleton para las operaciones ORM. */
    private static final SessionFactory SESSION_FACTORY = buildSessionFactory(CONNECTION_BEAN);

    /** Constructor privado para clase utilitaria estática. */
    private HibernateUtil() {
    }

    /**
     * @return fábrica de sesiones ORM de la aplicación.
     */
    public static SessionFactory getSessionFactory() {
        return SESSION_FACTORY;
    }

    /**
     * @return bean de conexión reutilizable desde cualquier clase.
     */
    public static DbConnectionBean getConnectionBean() {
        return CONNECTION_BEAN;
    }

    /**
     * Construye configuración por defecto del bean de conexión.
     *
     * @return bean configurado con valores de la práctica.
     */
    private static DbConnectionBean buildDefaultConnectionBean() {
        DbConnectionBean bean = new DbConnectionBean();
        bean.setShowSql(false);
        bean.setFormatSql(false);
        bean.setHighlightSql(false);
        return bean;
    }

    /**
     * Crea {@link SessionFactory} a partir de un bean de conexión.
     *
     * @param connectionBean bean con configuración JDBC/Hibernate.
     * @return fábrica de sesiones lista para usar.
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
