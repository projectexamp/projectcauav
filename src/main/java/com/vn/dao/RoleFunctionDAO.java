package com.vn.dao;

import com.vn.entity.Functions;
import com.vn.entity.RoleFunction;
import com.vn.entity.RoleUser;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class RoleFunctionDAO {
    @Autowired
    private SessionFactory sessionFactory;
    public boolean addRoleFunction(RoleFunction roleFunction) {
        Session session = sessionFactory.getCurrentSession();
        try {
            session.save(roleFunction);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean updateRoleFunction(RoleFunction roleFunction) {
        Session session = sessionFactory.getCurrentSession();
        try {
            session.update(roleFunction);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public RoleFunction findbyId(int id) {
        Session session = sessionFactory.getCurrentSession();
        try {
            RoleFunction roleFunction = (RoleFunction) session.get(RoleFunction.class, id);
            session.flush();
            return roleFunction;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public boolean deleteRoleFunction(RoleFunction roleFunction) {
        Session session = sessionFactory.getCurrentSession();
        try {
            session.delete(roleFunction);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public List<RoleFunction> lstFunction(int roleId) {
        Session session = sessionFactory.getCurrentSession();
        String sql = "Select rf from " + RoleFunction.class.getName() + " rf  where rf.isActive = 1 and rf.role.roleId =:roleId";
        Query query = session.createQuery(sql, RoleFunction.class);
        query.setParameter("roleId", roleId);
        return query.getResultList();
    }
}
