package com.university.assignment.model;

public class Assignment {

    private String studentName;
    private String studentId;
    private String courseName;
    private String assignmentTitle;
    private String content;

    public Assignment() {
    }

    public Assignment(String studentName, String studentId, String courseName, String assignmentTitle, String content) {
        this.studentName = studentName;
        this.studentId = studentId;
        this.courseName = courseName;
        this.assignmentTitle = assignmentTitle;
        this.content = content;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getAssignmentTitle() {
        return assignmentTitle;
    }

    public void setAssignmentTitle(String assignmentTitle) {
        this.assignmentTitle = assignmentTitle;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
