package org.blaiseSolutions.dao;

import lombok.extern.slf4j.Slf4j;
import org.blaiseSolutions.model.Tasks;
import org.blaiseSolutions.model.User;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

@Slf4j
public class TasksDao {
    public void saveTask(Tasks task) {
        try {

            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(task);
            session.getTransaction().commit();
            session.close();

        } catch (Exception exception) {
            exception.printStackTrace();
        }


    }

    public Tasks updateTask(Tasks task) {
        try {

            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(task);
            session.getTransaction().commit();
            session.close();
            return task;
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public void deleteTask(Tasks task) {
        try {

            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(task);
            session.getTransaction().commit();
            session.close();

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
    public List<Tasks> findAllTask() {
        try {
            Session ss = HibernateUtil.getSessionFactory().openSession();
            ss.beginTransaction();
            List<Tasks> tasksList = ss.createQuery("FROM Tasks ", Tasks.class).getResultList();
            ss.close();
            return tasksList;
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }
    public List<String> findTaskDescriptionsByUser(User user) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "SELECT t.description FROM Tasks t JOIN t.usersList u WHERE u = :user";
            Query<String> query = session.createQuery(hql, String.class);
            query.setParameter("user", user);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
