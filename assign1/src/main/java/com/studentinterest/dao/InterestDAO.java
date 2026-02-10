package com.studentinterest.dao;

import com.studentinterest.entity.Interest;
import com.studentinterest.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InterestDAO {
    
    // Create or Update Interest
    public void saveOrUpdate(Interest interest) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.saveOrUpdate(interest);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
    
    // Get Interest by ID
    public Interest getById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Interest.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    // Get all Interests
    public List<Interest> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Interest> query = session.createQuery("FROM Interest", Interest.class);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    // Delete Interest
    public void delete(Long id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Interest interest = session.get(Interest.class, id);
            if (interest != null) {
                session.delete(interest);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
    
    // Get interests by category
    public List<Interest> getByCategory(String category) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Interest> query = session.createQuery(
                "FROM Interest WHERE category = :category", Interest.class);
            query.setParameter("category", category);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    // Get all categories
    public List<String> getAllCategories() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<String> query = session.createQuery(
                "SELECT DISTINCT category FROM Interest", String.class);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    // Part B: Get student count per interest category
    public Map<String, Long> getStudentCountByCategory() {
        Map<String, Long> result = new HashMap<>();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "SELECT i.category, COUNT(DISTINCT s.studentId) " +
                        "FROM Interest i " +
                        "LEFT JOIN i.students s " +
                        "GROUP BY i.category";
            Query<Object[]> query = session.createQuery(hql, Object[].class);
            List<Object[]> resultList = query.list();
            
            for (Object[] row : resultList) {
                String category = (String) row[0];
                Long count = (Long) row[1];
                result.put(category, count);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
