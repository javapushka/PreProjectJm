package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        Statement statement = null;
        try {
            statement = Util.getMySQLConnection().createStatement();
            int check = statement.executeUpdate("SHOW TABLES LIKE 'users'");
            if (check == 0) {
                statement.executeUpdate("CREATE TABLE users (id BIGINT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(30), " +
                        "lastname VARCHAR (30), age TINYINT UNSIGNED)");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void dropUsersTable() {
        Statement statement = null;
        try {
            statement = Util.getMySQLConnection().createStatement();
            int check = statement.executeUpdate("SHOW TABLES LIKE 'users'");
            if (check != 0) {
                statement.executeUpdate("DROP TABLE users");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = null;
//        Transaction transaction = null;
        try {
            session = Util.getHibernateConnection().openSession();
            session.beginTransaction();
//            transaction = session.getTransaction();
//            transaction.begin();
            User user = new User(name, lastName, age);
            session.save(user);
            System.out.printf("User с именем - %s добавлен в базу данных.\n", name);
            session.getTransaction().commit();
//            transaction.commit();
        }
//        catch (Exception e) {
//            if (transaction != null) {
//                transaction.rollback();
//            }
//            e.printStackTrace();}
        finally {
            if (session != null) {
                session.close();
            }
        }

    }

    @Override
    public void removeUserById(long id) {
        Session session = null;
        try {
            session = Util.getHibernateConnection().openSession();
            session.beginTransaction();

            User user = session.get(User.class, id);
            session.remove(user);
            System.out.println("User id " + user.getId() + " was delete");

            session.getTransaction().commit();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        Session session = null;
        List<User> usersList;
        try {
            session = Util.getHibernateConnection().openSession();
            session.beginTransaction();

            usersList = session.createQuery("from User").getResultList();

            session.getTransaction().commit();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return usersList;
    }

    @Override
    public void cleanUsersTable() {
        Session session = null;
        try {
            session = Util.getHibernateConnection().openSession();
            session.beginTransaction();

            session.createQuery("delete User").executeUpdate();
            System.out.println("Table is clear!");

            session.getTransaction().commit();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
