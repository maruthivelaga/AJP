package com.university.assignment.dao;

import com.university.assignment.model.Assignment;
import com.university.assignment.util.DatabaseUtil;

import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AssignmentDao {

    public AssignmentDao() {
        initializeTable();
    }

    public void saveAssignment(Assignment assignment) throws SQLException {
        String sql = "INSERT INTO assignment_submissions "
                + "(student_name, student_id, course_name, assignment_title, content) "
                + "VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, assignment.getStudentName());
            preparedStatement.setString(2, assignment.getStudentId());
            preparedStatement.setString(3, assignment.getCourseName());
            preparedStatement.setString(4, assignment.getAssignmentTitle());
            preparedStatement.setString(5, assignment.getContent());

            preparedStatement.executeUpdate();
        }
    }

    public List<Assignment> getAllAssignments() throws SQLException {
        String sql = "SELECT student_name, student_id, course_name, assignment_title, content "
                + "FROM assignment_submissions ORDER BY submitted_at DESC";

        List<Assignment> assignments = new ArrayList<Assignment>();

        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Assignment assignment = new Assignment();
                assignment.setStudentName(resultSet.getString("student_name"));
                assignment.setStudentId(resultSet.getString("student_id"));
                assignment.setCourseName(resultSet.getString("course_name"));
                assignment.setAssignmentTitle(resultSet.getString("assignment_title"));
                assignment.setContent(resultSet.getString("content"));
                assignments.add(assignment);
            }
        }

        return assignments;
    }

    private void initializeTable() {
        String ddl = "CREATE TABLE IF NOT EXISTS assignment_submissions ("
                + "id INT AUTO_INCREMENT PRIMARY KEY,"
                + "student_name VARCHAR(100) NOT NULL,"
                + "student_id VARCHAR(20) NOT NULL,"
                + "course_name VARCHAR(100) NOT NULL,"
                + "assignment_title VARCHAR(200) NOT NULL,"
                + "content CLOB NOT NULL,"
                + "submitted_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP"
                + ")";

        try (Connection connection = DatabaseUtil.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(ddl);
        } catch (SQLException exception) {
            throw new RuntimeException("Failed to initialize database table.", exception);
        }
    }
}
