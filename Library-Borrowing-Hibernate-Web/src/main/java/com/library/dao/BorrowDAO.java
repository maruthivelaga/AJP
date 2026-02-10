package com.library.dao;

import com.library.entity.Borrow;
import com.library.entity.Borrow.BorrowStatus;
import com.library.entity.Member;
import com.library.entity.Book;
import com.library.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.time.LocalDate;
import java.util.List;

/**
 * DAO for Borrow operations
 */
public class BorrowDAO {

    public boolean issueBook(Long memberId, Long bookId, LocalDate issueDate, LocalDate dueDate) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            
            Member member = session.get(Member.class, memberId);
            Book book = session.get(Book.class, bookId);
            
            if (member != null && book != null) {
                Borrow borrow = new Borrow(member, book, issueDate, dueDate, BorrowStatus.ISSUED);
                session.save(borrow);
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

    public boolean returnBook(Long borrowId) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            
            Borrow borrow = session.get(Borrow.class, borrowId);
            if (borrow != null) {
                borrow.setStatus(BorrowStatus.RETURNED);
                session.update(borrow);
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

    public Borrow getBorrowById(Long borrowId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Borrow.class, borrowId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Borrow> getAllBorrows() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Borrow ORDER BY issueDate DESC", Borrow.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Borrow> getBorrowsByMember(Long memberId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Borrow> query = session.createQuery(
                "FROM Borrow WHERE member.memberId = :memberId ORDER BY issueDate DESC", Borrow.class);
            query.setParameter("memberId", memberId);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Borrow> getBorrowsByBook(Long bookId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Borrow> query = session.createQuery(
                "FROM Borrow WHERE book.bookId = :bookId ORDER BY issueDate DESC", Borrow.class);
            query.setParameter("bookId", bookId);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Borrow> getCurrentlyBorrowedByMember(Long memberId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Borrow> query = session.createQuery(
                "FROM Borrow WHERE member.memberId = :memberId AND status = :status ORDER BY issueDate DESC", 
                Borrow.class);
            query.setParameter("memberId", memberId);
            query.setParameter("status", BorrowStatus.ISSUED);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Borrow> getOverdueBorrows() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Borrow> query = session.createQuery(
                "FROM Borrow WHERE status = :status AND dueDate < :today ORDER BY dueDate ASC", 
                Borrow.class);
            query.setParameter("status", BorrowStatus.ISSUED);
            query.setParameter("today", LocalDate.now());
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Borrow> getBorrowsByStatus(BorrowStatus status) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Borrow> query = session.createQuery(
                "FROM Borrow WHERE status = :status ORDER BY issueDate DESC", Borrow.class);
            query.setParameter("status", status);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean deleteBorrow(Long borrowId) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Borrow borrow = session.get(Borrow.class, borrowId);
            if (borrow != null) {
                session.delete(borrow);
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
}
