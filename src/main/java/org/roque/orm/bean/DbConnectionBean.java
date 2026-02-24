package org.roque.orm.bean;

import java.util.Properties;

/**
 * JavaBean reutilizable que centraliza la configuración de conexión para Hibernate ORM.
 * <p>
 * Este bean permite definir (y reutilizar desde cualquier clase) los parámetros básicos de
 * conexión JDBC y opciones de generación/visualización SQL.
 */
public class DbConnectionBean {

    private String driverClass = "org.h2.Driver";
    private String jdbcUrl = "jdbc:h2:mem:biblioteca_orm;DB_CLOSE_DELAY=-1";
    private String username = "sa";
    private String password = "";
    private String hbm2ddlAuto = "create-drop";
    private boolean showSql = false;
    private boolean formatSql = false;
    private boolean highlightSql = false;

    /**
     * Crea un bean con valores por defecto para la práctica.
     */
    public DbConnectionBean() {
    }

    /**
     * @return clase del driver JDBC.
     */
    public String getDriverClass() {
        return driverClass;
    }

    /**
     * @param driverClass clase del driver JDBC.
     */
    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }

    /**
     * @return URL JDBC de la base de datos.
     */
    public String getJdbcUrl() {
        return jdbcUrl;
    }

    /**
     * @param jdbcUrl URL JDBC de la base de datos.
     */
    public void setJdbcUrl(String jdbcUrl) {
        this.jdbcUrl = jdbcUrl;
    }

    /**
     * @return nombre de usuario JDBC.
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username nombre de usuario JDBC.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return contraseña JDBC.
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password contraseña JDBC.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return estrategia de generación DDL de Hibernate (`hibernate.hbm2ddl.auto`).
     */
    public String getHbm2ddlAuto() {
        return hbm2ddlAuto;
    }

    /**
     * @param hbm2ddlAuto estrategia de generación DDL (`create`, `update`, `create-drop`, etc.).
     */
    public void setHbm2ddlAuto(String hbm2ddlAuto) {
        this.hbm2ddlAuto = hbm2ddlAuto;
    }

    /**
     * @return {@code true} si Hibernate debe mostrar SQL en consola.
     */
    public boolean isShowSql() {
        return showSql;
    }

    /**
     * @param showSql indica si Hibernate debe mostrar SQL en consola.
     */
    public void setShowSql(boolean showSql) {
        this.showSql = showSql;
    }

    /**
     * @return {@code true} si Hibernate debe formatear SQL mostrado.
     */
    public boolean isFormatSql() {
        return formatSql;
    }

    /**
     * @param formatSql indica si Hibernate debe formatear SQL mostrado.
     */
    public void setFormatSql(boolean formatSql) {
        this.formatSql = formatSql;
    }

    /**
     * @return {@code true} si Hibernate debe colorear SQL en consola.
     */
    public boolean isHighlightSql() {
        return highlightSql;
    }

    /**
     * @param highlightSql indica si Hibernate debe colorear SQL en consola.
     */
    public void setHighlightSql(boolean highlightSql) {
        this.highlightSql = highlightSql;
    }

    /**
     * Convierte el bean a {@link Properties} entendibles por Hibernate.
     *
     * @return propiedades de configuración para `StandardServiceRegistryBuilder.applySettings(...)`.
     */
    public Properties toHibernateProperties() {
        Properties settings = new Properties();
        settings.put("hibernate.connection.driver_class", driverClass);
        settings.put("hibernate.connection.url", jdbcUrl);
        settings.put("hibernate.connection.username", username);
        settings.put("hibernate.connection.password", password);
        settings.put("hibernate.hbm2ddl.auto", hbm2ddlAuto);
        settings.put("hibernate.show_sql", String.valueOf(showSql));
        settings.put("hibernate.format_sql", String.valueOf(formatSql));
        settings.put("hibernate.highlight_sql", String.valueOf(highlightSql));
        return settings;
    }
}
