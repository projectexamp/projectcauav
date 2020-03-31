package com.vn.dao;

import com.vn.entity.Functions;
import com.vn.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;

import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class FuctionDAO {
    @Autowired
    private SessionFactory sessionFactory;
    public List<Functions> findAll() {
        Session session = sessionFactory.getCurrentSession();
//        List<Function> list =  session.createQuery("from Function").list();
        String sql = "Select f from " + Functions.class.getName() + " f  where f.status = 1";
        Query query = session.createQuery(sql, Functions.class);
        session.flush();
        return query.getResultList();
//        return list;
    }
    @Transactional
    public Functions findById(int id) {
        Session session = sessionFactory.getCurrentSession();
        try {
            Functions function = (Functions) session.get(Functions.class, id);
            session.flush();
            return function;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    @Transactional
    public boolean add(Functions functions) {
        Session session = sessionFactory.getCurrentSession();
        try {
            session.save(functions);
            session.flush();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    @Transactional
    public boolean update(Functions functions) {
        Session session = sessionFactory.getCurrentSession();
        try {
            session.update(functions);
            session.flush();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public List<Functions> findLstFunction(String functionName) {
        Session session = sessionFactory.getCurrentSession();
        try {
            String sql = "Select f from " + Functions.class.getName() + " f " //
                    + " Where f.functionName like concat('%',:functionName,'%') and f.status = 1";
            Query query = session.createQuery(sql, Functions.class);
            query.setParameter("functionName", functionName);
            return (List<Functions>) query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
