package com.library.servlet;

import com.library.dao.BorrowDAO;
import com.library.dao.MemberDAO;
import com.library.dao.BookDAO;
import com.library.entity.Borrow;
import com.library.entity.Member;
import com.library.entity.Book;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@WebServlet("/borrow")
public class BorrowServlet extends HttpServlet {
    private BorrowDAO borrowDAO = new BorrowDAO();
    private MemberDAO memberDAO = new MemberDAO();
    private BookDAO bookDAO = new BookDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) action = "list";
        
        switch (action) {
            case "list":
                listBorrows(request, response);
                break;
            case "issue":
                showIssueForm(request, response);
                break;
            case "return":
                returnBook(request, response);
                break;
            case "memberBorrows":
                showMemberBorrows(request, response);
                break;
            case "bookHistory":
                showBookHistory(request, response);
                break;
            case "overdue":
                showOverdueBorrows(request, response);
                break;
            default:
                listBorrows(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        
        if ("issue".equals(action)) {
            issueBook(request, response);
        } else {
            listBorrows(request, response);
        }
    }

    private void listBorrows(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Borrow> borrows = borrowDAO.getAllBorrows();
        request.setAttribute("borrows", borrows);
        request.getRequestDispatcher("/borrows.jsp").forward(request, response);
    }

    private void showIssueForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Member> members = memberDAO.getAllMembers();
        List<Book> books = bookDAO.getAllBooks();
        request.setAttribute("members", members);
        request.setAttribute("books", books);
        request.getRequestDispatcher("/issue-book.jsp").forward(request, response);
    }

    private void issueBook(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long memberId = Long.parseLong(request.getParameter("memberId"));
        Long bookId = Long.parseLong(request.getParameter("bookId"));
        LocalDate issueDate = LocalDate.parse(request.getParameter("issueDate"));
        LocalDate dueDate = LocalDate.parse(request.getParameter("dueDate"));
        
        boolean success = borrowDAO.issueBook(memberId, bookId, issueDate, dueDate);
        request.setAttribute("message", success ? "Book issued successfully!" : "Error issuing book.");
        listBorrows(request, response);
    }

    private void returnBook(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long borrowId = Long.parseLong(request.getParameter("id"));
        boolean success = borrowDAO.returnBook(borrowId);
        
        request.setAttribute("message", success ? "Book returned successfully!" : "Error returning book.");
        listBorrows(request, response);
    }

    private void showMemberBorrows(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long memberId = Long.parseLong(request.getParameter("id"));
        Member member = memberDAO.getMemberById(memberId);
        List<Borrow> currentBorrows = borrowDAO.getCurrentlyBorrowedByMember(memberId);
        List<Borrow> allBorrows = borrowDAO.getBorrowsByMember(memberId);
        
        request.setAttribute("member", member);
        request.setAttribute("currentBorrows", currentBorrows);
        request.setAttribute("allBorrows", allBorrows);
        request.getRequestDispatcher("/member-borrows.jsp").forward(request, response);
    }

    private void showBookHistory(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long bookId = Long.parseLong(request.getParameter("id"));
        Book book = bookDAO.getBookById(bookId);
        List<Borrow> history = borrowDAO.getBorrowsByBook(bookId);
        
        request.setAttribute("book", book);
        request.setAttribute("history", history);
        request.getRequestDispatcher("/book-history.jsp").forward(request, response);
    }

    private void showOverdueBorrows(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Borrow> overdueBorrows = borrowDAO.getOverdueBorrows();
        request.setAttribute("overdueBorrows", overdueBorrows);
        request.getRequestDispatcher("/overdue.jsp").forward(request, response);
    }
}
