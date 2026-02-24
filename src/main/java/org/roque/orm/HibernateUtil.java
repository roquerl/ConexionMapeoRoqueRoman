package org.roque.orm;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.roque.orm.entity.Autor;
import org.roque.orm.entity.Libro;
import org.roque.orm.entity.Prestamo;

import java.util.Properties;

public final class HibernateUtil {

    private static final SessionFactory SESSION_FACTORY = buildSessionFactory();

    private HibernateUtil() {
    }

    public static SessionFactory getSessionFactory() {
        return SESSION_FACTORY;
    }

    private static SessionFactory buildSessionFactory() {
        Properties settings = new Properties();
        settings.put("hibernate.connection.driver_class", "org.h2.Driver");
        settings.put("hibernate.connection.url", "jdbc:h2:mem:biblioteca_orm;DB_CLOSE_DELAY=-1");
        settings.put("hibernate.connection.username", "sa");
        settings.put("hibernate.connection.password", "");
        settings.put("hibernate.hbm2ddl.auto", "create-drop");
        settings.put("hibernate.show_sql", "true");
        settings.put("hibernate.format_sql", "true");
        settings.put("hibernate.highlight_sql", "true");

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
