package com.vn.dao;

import com.vn.entity.Functions;
import com.vn.entity.Role;
import com.vn.entity.RoleUser;
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
public class RoleDAO {
    @Autowired
    private SessionFactory sessionFactory;
    public List<String> getRoleNames(int userId) {
        try {
            Session session = sessionFactory.getCurrentSession();
            String sql = "Select ur.role.roleName from " + RoleUser.class.getName() + " ur " //
                    + " where ur.user.userId = :userId ";
            Query query = session.createQuery(sql, String.class);
            query.setParameter("userId", userId);
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
    public List<Role> findAll() {
        Session session = sessionFactory.getCurrentSession();
//        List<Function> list =  session.createQuery("from Function").list();
        String sql = "Select r from " + Role.class.getName() + " r  where r.status = 1";
        Query query = session.createQuery(sql, Role.class);
        session.flush();
        return query.getResultList();
//        return list;
    }
    @Transactional
    public Role findById(int id) {
        Session session = sessionFactory.getCurrentSession();
        try {
            Role role = (Role) session.get(Role.class, id);
            session.flush();
            return role;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    @Transactional
    public Integer add(Role role) {
        Session session = sessionFactory.getCurrentSession();
        try {
            return (Integer) session.save(role);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    @Transactional
    public boolean update(Role role) {
        Session session = sessionFactory.getCurrentSession();
        try {
            session.update(role);
            session.flush();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public List<Role> findLstRole(String roleName) {
        Session session = sessionFactory.getCurrentSession();
        try {
            String sql = "Select r from " + Role.class.getName() + " r " //
                    + " Where r.roleName like concat('%',:roleName,'%') and r.status = 1";
            Query query = session.createQuery(sql, Role.class);
            query.setParameter("roleName", roleName);
            return (List<Role>) query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public List<Role> findCheckedRole(Integer userId) {
        Session session = sessionFactory.getCurrentSession();
        try {
            String sql = "Select r from " + Role.class.getName() + " r " //
                    + " Where r.userId =:userId and r.status = 1";
            Query query = session.createQuery(sql, Role.class);
            query.setParameter("userId", userId);
            return (List<Role>) query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
