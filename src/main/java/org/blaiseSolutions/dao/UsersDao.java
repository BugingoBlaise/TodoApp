package org.blaiseSolutions.dao;

import org.blaiseSolutions.model.User;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.scheduling.config.Task;

import java.util.List;

public class UsersDao {
    public void saveUser(User user) {
        try {

            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
            session.close();

        } catch (Exception exception) {
            exception.printStackTrace();
        }


    }

    public User updateUser(User user) {
        try {

            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(user);
            session.getTransaction().commit();
            session.close();
            return user;
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public void deleteUser(User user) {
        try {

            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(user);
            session.getTransaction().commit();
            session.close();

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public List<User> findAllUsers() {
        try {

            Session ss = HibernateUtil.getSessionFactory().openSession();
            List<User> userList = ss.createQuery("FROM User ", User.class).getResultList();
            ss.close();

            return userList;




        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }

   /* public List<Task> findTaskDescriptionByUser(Task task) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "SELECT u.assignedTasks FROM User u WHERE u.username = :user";
            Query<Task> query = session.createQuery(hql, Task.class);
            query.setParameter("user",);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    */
}
