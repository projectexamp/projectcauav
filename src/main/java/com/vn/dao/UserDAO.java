package com.vn.dao;

import com.vn.entity.Role;
import com.vn.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class UserDAO {
    @Autowired
    private SessionFactory sessionFactory;

    public User findUserAccount(String userName) {
        try {
            Session session = sessionFactory.getCurrentSession();
            String sql = "Select e from " + User.class.getName() + " e " //
                    + " Where e.userName = :userName ";

            Query query = session.createQuery(sql, User.class);
            query.setParameter("userName", userName);

            return (User) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
    public List<User> findAll() {
        Session session = sessionFactory.getCurrentSession();
        String sql = "Select u from " + User.class.getName() + " u  where u.status = 1";
        Query query = session.createQuery(sql, User.class);
        session.flush();
        return query.getResultList();
    }
    @Transactional
    public User findById(int id) {
        Session session = sessionFactory.getCurrentSession();
        try {
            User user = (User) session.get(User.class, id);
            session.flush();
            return user;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    @Transactional
    public boolean add(User user) {
        Session session = sessionFactory.getCurrentSession();
        try {
            session.save(user);
            session.flush();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    @Transactional
    public boolean update(User user) {
        Session session = sessionFactory.getCurrentSession();
        try {
            session.update(user);
            session.flush();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public List<User> findLstUser(String userName) {
        Session session = sessionFactory.getCurrentSession();
        try {
            String sql = "Select r from " + User.class.getName() + " r " //
                    + " Where r.userName like concat('%',:userName,'%') and r.status = 1";
            Query query = session.createQuery(sql, User.class);
            query.setParameter("userName", userName);
            return (List<User>) query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
