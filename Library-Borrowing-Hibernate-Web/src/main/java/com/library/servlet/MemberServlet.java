package com.library.servlet;

import com.library.dao.MemberDAO;
import com.library.entity.Member;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/member")
public class MemberServlet extends HttpServlet {
    private MemberDAO memberDAO = new MemberDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) action = "list";
        
        switch (action) {
            case "list":
                listMembers(request, response);
                break;
            case "add":
                showAddForm(request, response);
                break;
            case "edit":
                showEditForm(request, response);
                break;
            case "delete":
                deleteMember(request, response);
                break;
            default:
                listMembers(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        
        switch (action) {
            case "add":
                addMember(request, response);
                break;
            case "update":
                updateMember(request, response);
                break;
            default:
                listMembers(request, response);
        }
    }

    private void listMembers(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Member> members = memberDAO.getAllMembers();
        request.setAttribute("members", members);
        request.getRequestDispatcher("/members.jsp").forward(request, response);
    }

    private void showAddForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/member-form.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long memberId = Long.parseLong(request.getParameter("id"));
        Member member = memberDAO.getMemberById(memberId);
        request.setAttribute("member", member);
        request.getRequestDispatcher("/member-form.jsp").forward(request, response);
    }

    private void addMember(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("name");
        int age = Integer.parseInt(request.getParameter("age"));
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        
        Member member = new Member(name, age, phone, email);
        boolean success = memberDAO.addMember(member);
        
        request.setAttribute("message", success ? "Member added successfully!" : "Error adding member.");
        listMembers(request, response);
    }

    private void updateMember(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long memberId = Long.parseLong(request.getParameter("memberId"));
        String name = request.getParameter("name");
        int age = Integer.parseInt(request.getParameter("age"));
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        
        Member member = memberDAO.getMemberById(memberId);
        member.setName(name);
        member.setAge(age);
        member.setPhone(phone);
        member.setEmail(email);
        
        boolean success = memberDAO.updateMember(member);
        request.setAttribute("message", success ? "Member updated successfully!" : "Error updating member.");
        listMembers(request, response);
    }

    private void deleteMember(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long memberId = Long.parseLong(request.getParameter("id"));
        boolean success = memberDAO.deleteMember(memberId);
        
        request.setAttribute("message", success ? "Member deleted successfully!" : "Error deleting member.");
        listMembers(request, response);
    }
}
