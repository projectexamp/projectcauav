package com.vn.dao;

import com.vn.entity.RoleUser;
import com.vn.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class RoleUserDAO {
    @Autowired
    private SessionFactory sessionFactory;

    public boolean addUserRole(RoleUser roleUser) {
        Session session = sessionFactory.getCurrentSession();
        try {
            session.save(roleUser);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateUserRole(RoleUser roleUser) {
        Session session = sessionFactory.getCurrentSession();
        try {
            session.update(roleUser);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(RoleUser roleUser) {
        Session session = sessionFactory.getCurrentSession();
        try {
            session.delete(roleUser);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public RoleUser findbyId(int id) {
        Session session = sessionFactory.getCurrentSession();
        try {
            RoleUser roleUser = (RoleUser) session.get(RoleUser.class, id);
            session.flush();
            return roleUser;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<RoleUser> findListRoleUser(Integer userId) {
        Session session = sessionFactory.getCurrentSession();
        String sql = "Select ru from " + RoleUser.class.getName() + " ru  where ru.isActive = 1 and ru.user.userId =:userId";
        Query query = session.createQuery(sql, RoleUser.class);
        query.setParameter("userId", userId);
        return query.getResultList();
    }
}
