package com.university.assignment.action;

import com.university.assignment.dao.AssignmentDao;
import com.university.assignment.form.AssignmentForm;
import com.university.assignment.model.Assignment;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SubmitAssignmentAction extends Action {

    private final AssignmentDao assignmentDao = new AssignmentDao();

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) {
        AssignmentForm assignmentForm = (AssignmentForm) form;

        Assignment assignment = new Assignment(
            safeValue(assignmentForm.getStudentName()),
            safeValue(assignmentForm.getStudentId()),
            safeValue(assignmentForm.getCourseName()),
            safeValue(assignmentForm.getAssignmentTitle()),
            safeValue(assignmentForm.getContent())
        );

        try {
            assignmentDao.saveAssignment(assignment);
            request.setAttribute("studentName", assignment.getStudentName());
            request.setAttribute("assignmentTitle", assignment.getAssignmentTitle());
            return mapping.findForward("success");
        } catch (Exception exception) {
            ActionErrors errors = new ActionErrors();
            errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.submission.failed"));
            saveErrors(request, errors);
            return mapping.findForward("failure");
        }
    }

    private String safeValue(String value) {
        return value == null ? "" : value;
    }
}
