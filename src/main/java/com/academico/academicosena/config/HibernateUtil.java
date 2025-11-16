package com.academico.academicosena.config;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.HashMap;
import java.util.Map;

public class HibernateUtil {

    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        // Try to read DB configuration from environment variables. If present, build programmatically.
        String dbUrl = System.getenv("JDBC_DATABASE_URL");
        String dbUser = System.getenv("JDBC_DATABASE_USERNAME");
        String dbPassword = System.getenv("JDBC_DATABASE_PASSWORD");
        String dialect = System.getenv("HIBERNATE_DIALECT");
        String hbm2ddl = System.getenv("HIBERNATE_HBM2DDL");
        String showSql = System.getenv("HIBERNATE_SHOW_SQL");

        try {
            StandardServiceRegistry registry;

            if (dbUrl != null && !dbUrl.isEmpty()) {
                Map<String, Object> settings = new HashMap<>();
                settings.put("hibernate.connection.url", dbUrl);
                if (dbUser != null) settings.put("hibernate.connection.username", dbUser);
                if (dbPassword != null) settings.put("hibernate.connection.password", dbPassword);

                // sensible defaults if not provided
                settings.put("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
                settings.put("hibernate.dialect", dialect != null ? dialect : "org.hibernate.dialect.MySQL8Dialect");
                settings.put("hibernate.hbm2ddl.auto", hbm2ddl != null ? hbm2ddl : "validate");
                settings.put("hibernate.show_sql", showSql != null ? showSql : "false");

                StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
                builder.applySettings(settings);
                registry = builder.build();

            } else {
                // Fallback to hibernate.cfg.xml on classpath (developer can keep local file)
                registry = new StandardServiceRegistryBuilder()
                        .configure("hibernate.cfg.xml")
                        .build();
            }

            return new MetadataSources(registry)
                    .buildMetadata()
                    .buildSessionFactory();

        } catch (Exception ex) {
            System.err.println("Error building SessionFactory: " + ex.getMessage());
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
