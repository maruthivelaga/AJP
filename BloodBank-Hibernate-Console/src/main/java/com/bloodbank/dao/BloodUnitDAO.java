package com.bloodbank.dao;

import com.bloodbank.entity.BloodUnit;
import com.bloodbank.entity.BloodUnit.*;
import com.bloodbank.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Data Access Object for BloodUnit operations
 */
public class BloodUnitDAO {

    /**
     * Add a new blood unit
     */
    public boolean addBloodUnit(BloodUnit bloodUnit) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(bloodUnit);
            transaction.commit();
            System.out.println("Blood unit added successfully with ID: " + bloodUnit.getUnitId());
            return true;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.err.println("Error adding blood unit: " + e.getMessage());
            return false;
        }
    }

    /**
     * Get a blood unit by ID
     */
    public BloodUnit getBloodUnitById(Long unitId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(BloodUnit.class, unitId);
        } catch (Exception e) {
            System.err.println("Error retrieving blood unit: " + e.getMessage());
            return null;
        }
    }

    /**
     * Get all blood units
     */
    public List<BloodUnit> getAllBloodUnits() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM BloodUnit", BloodUnit.class).list();
        } catch (Exception e) {
            System.err.println("Error retrieving blood units: " + e.getMessage());
            return null;
        }
    }

    /**
     * Update a blood unit
     */
    public boolean updateBloodUnit(BloodUnit bloodUnit) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(bloodUnit);
            transaction.commit();
            System.out.println("Blood unit updated successfully!");
            return true;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.err.println("Error updating blood unit: " + e.getMessage());
            return false;
        }
    }

    /**
     * Delete a blood unit
     */
    public boolean deleteBloodUnit(Long unitId) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            BloodUnit bloodUnit = session.get(BloodUnit.class, unitId);
            if (bloodUnit != null) {
                session.delete(bloodUnit);
                transaction.commit();
                System.out.println("Blood unit deleted successfully!");
                return true;
            } else {
                System.out.println("Blood unit not found!");
                return false;
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.err.println("Error deleting blood unit: " + e.getMessage());
            return false;
        }
    }

    /**
     * Search blood units by blood type
     */
    public List<BloodUnit> searchByBloodType(BloodType bloodType) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<BloodUnit> query = session.createQuery(
                "FROM BloodUnit WHERE bloodType = :bloodType", BloodUnit.class);
            query.setParameter("bloodType", bloodType);
            return query.list();
        } catch (Exception e) {
            System.err.println("Error searching by blood type: " + e.getMessage());
            return null;
        }
    }

    /**
     * Search blood units by rhesus factor
     */
    public List<BloodUnit> searchByRhesus(Rhesus rhesus) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<BloodUnit> query = session.createQuery(
                "FROM BloodUnit WHERE rhesus = :rhesus", BloodUnit.class);
            query.setParameter("rhesus", rhesus);
            return query.list();
        } catch (Exception e) {
            System.err.println("Error searching by rhesus: " + e.getMessage());
            return null;
        }
    }

    /**
     * Search blood units by blood type and rhesus
     */
    public List<BloodUnit> searchByBloodTypeAndRhesus(BloodType bloodType, Rhesus rhesus) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<BloodUnit> query = session.createQuery(
                "FROM BloodUnit WHERE bloodType = :bloodType AND rhesus = :rhesus", BloodUnit.class);
            query.setParameter("bloodType", bloodType);
            query.setParameter("rhesus", rhesus);
            return query.list();
        } catch (Exception e) {
            System.err.println("Error searching: " + e.getMessage());
            return null;
        }
    }

    /**
     * Get expired blood units
     */
    public List<BloodUnit> getExpiredUnits() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<BloodUnit> query = session.createQuery(
                "FROM BloodUnit WHERE expiryDate < :today", BloodUnit.class);
            query.setParameter("today", LocalDate.now());
            return query.list();
        } catch (Exception e) {
            System.err.println("Error retrieving expired units: " + e.getMessage());
            return null;
        }
    }

    /**
     * Get blood units by status
     */
    public List<BloodUnit> getBloodUnitsByStatus(Status status) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<BloodUnit> query = session.createQuery(
                "FROM BloodUnit WHERE status = :status", BloodUnit.class);
            query.setParameter("status", status);
            return query.list();
        } catch (Exception e) {
            System.err.println("Error retrieving units by status: " + e.getMessage());
            return null;
        }
    }

    /**
     * Generate summary report: count of available units by blood type and rhesus
     */
    public Map<String, Long> getSummaryReport() {
        Map<String, Long> summary = new HashMap<>();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Object[]> query = session.createQuery(
                "SELECT bloodType, rhesus, COUNT(*) FROM BloodUnit " +
                "WHERE status = :status GROUP BY bloodType, rhesus", Object[].class);
            query.setParameter("status", Status.AVAILABLE);
            
            List<Object[]> results = query.list();
            for (Object[] row : results) {
                BloodType bloodType = (BloodType) row[0];
                Rhesus rhesus = (Rhesus) row[1];
                Long count = (Long) row[2];
                String key = bloodType.toString() + rhesus.getSymbol();
                summary.put(key, count);
            }
        } catch (Exception e) {
            System.err.println("Error generating summary: " + e.getMessage());
        }
        return summary;
    }

    /**
     * Mark a unit as ISSUED
     */
    public boolean issueBloodUnit(Long unitId) {
        BloodUnit unit = getBloodUnitById(unitId);
        if (unit != null && unit.getStatus() == Status.AVAILABLE) {
            unit.setStatus(Status.ISSUED);
            return updateBloodUnit(unit);
        } else {
            System.out.println("Unit not available for issuance!");
            return false;
        }
    }
}
