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

public final class HibernateUtil {

    private static final DbConnectionBean CONNECTION_BEAN = buildDefaultConnectionBean();
    private static final SessionFactory SESSION_FACTORY = buildSessionFactory(CONNECTION_BEAN);

    private HibernateUtil() {
    }

    public static SessionFactory getSessionFactory() {
        return SESSION_FACTORY;
    }

    public static DbConnectionBean getConnectionBean() {
        return CONNECTION_BEAN;
    }

    private static DbConnectionBean buildDefaultConnectionBean() {
        DbConnectionBean bean = new DbConnectionBean();
        bean.setShowSql(false);
        bean.setFormatSql(false);
        bean.setHighlightSql(false);
        return bean;
    }

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
