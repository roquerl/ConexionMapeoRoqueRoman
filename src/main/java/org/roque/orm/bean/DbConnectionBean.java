package org.roque.orm.bean;

import java.util.Properties;

/**
 * Bean reutilizable para centralizar la configuración de conexión ORM.
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

    public DbConnectionBean() {
    }

    public String getDriverClass() {
        return driverClass;
    }

    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }

    public String getJdbcUrl() {
        return jdbcUrl;
    }

    public void setJdbcUrl(String jdbcUrl) {
        this.jdbcUrl = jdbcUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHbm2ddlAuto() {
        return hbm2ddlAuto;
    }

    public void setHbm2ddlAuto(String hbm2ddlAuto) {
        this.hbm2ddlAuto = hbm2ddlAuto;
    }

    public boolean isShowSql() {
        return showSql;
    }

    public void setShowSql(boolean showSql) {
        this.showSql = showSql;
    }

    public boolean isFormatSql() {
        return formatSql;
    }

    public void setFormatSql(boolean formatSql) {
        this.formatSql = formatSql;
    }

    public boolean isHighlightSql() {
        return highlightSql;
    }

    public void setHighlightSql(boolean highlightSql) {
        this.highlightSql = highlightSql;
    }

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
