package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    private static final String URL = "jdbc:mysql://localhost:3306/test?useSSL=false";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String USER = "root";
    private static final String PASSWORD = "mysqlqwerty";

    private static StandardServiceRegistry serviceRegistry;
    private static SessionFactory sessionFactory;

    public static Connection getMySQLConnection() {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            System.out.println("Проблемы с загрузкой драйвера подключения");
        }
        try {
            Connection connect = DriverManager.getConnection(URL, USER, PASSWORD);
            return connect;
        } catch (SQLException e) {
            System.out.println("Проблема с соединением");
        }
        return null;
    }

    public static SessionFactory getHibernateConnection() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();
                StandardServiceRegistryBuilder registryBuilder = new StandardServiceRegistryBuilder();

                Properties settings = new Properties();
//                Map<String, String> settings = new HashMap<>();
                settings.put("hibernate.connection.url", URL);
                settings.put("hibernate.connection.driver_class", DRIVER);
                settings.put("hibernate.connection.username", USER);
                settings.put("hibernate.connection.password", PASSWORD);
                settings.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
                settings.put("hibernate.show_sql", "true");
//                settings.put("hibernate.hbm2ddl.auto", "create-drop");
                settings.put("hibernate.hbm2ddl.auto", "update");
                settings.put("current_session_context_class", "thread");
//                settings.put("c3p0.min_size", "5");
//                settings.put("c3p0.max_size", "200");
//                settings.put("hibernate.c3p0.max_statements", "200");

                configuration.setProperties(settings);
                configuration.addAnnotatedClass(User.class);

                registryBuilder.applySettings(settings);
                serviceRegistry = registryBuilder.applySettings(configuration.getProperties()).build();

//                MetadataSources metadataSources = new MetadataSources(serviceRegistry)
//                        .addAnnotatedClass(User.class);
//                sessionFactory = metadataSources.buildMetadata().buildSessionFactory();

                sessionFactory = configuration.buildSessionFactory(serviceRegistry);

            } catch (Exception e) {
                System.out.println("SessionFactory creation failed");
                if (serviceRegistry != null) {
                    StandardServiceRegistryBuilder.destroy(serviceRegistry);
                }
            }
        }
        return sessionFactory;
    }

    public static void closeRegistry() {
        if (serviceRegistry != null) {
            StandardServiceRegistryBuilder.destroy(serviceRegistry);
        }
    }
}
