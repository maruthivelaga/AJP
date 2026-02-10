package com.student.dao;

import com.student.entity.Interest;
import com.student.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Data Access Object for Interest operations
 */
public class InterestDAO {

    public boolean addInterest(Interest interest) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(interest);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            return false;
        }
    }

    public Interest getInterestById(Long interestId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Interest.class, interestId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Interest> getAllInterests() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Interest", Interest.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean updateInterest(Interest interest) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(interest);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteInterest(Long interestId) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Interest interest = session.get(Interest.class, interestId);
            if (interest != null) {
                // Remove all student associations
                interest.getStudents().clear();
                session.update(interest);
                session.delete(interest);
                transaction.commit();
                return true;
            }
            return false;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Get report of student count by category
     */
    public Map<String, Long> getStudentCountByCategory() {
        Map<String, Long> report = new HashMap<>();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Object[]> query = session.createQuery(
                "SELECT i.category, COUNT(DISTINCT s.studentId) " +
                "FROM Interest i JOIN i.students s " +
                "GROUP BY i.category", Object[].class);
            
            List<Object[]> results = query.list();
            for (Object[] row : results) {
                String category = (String) row[0];
                Long count = (Long) row[1];
                report.put(category, count);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return report;
    }

    /**
     * Get interests by category
     */
    public List<Interest> getInterestsByCategory(String category) {
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
}
