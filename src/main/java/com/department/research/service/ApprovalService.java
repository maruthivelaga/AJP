package com.department.research.service;

import com.department.research.model.Publication;
import com.department.research.model.User;
import com.department.research.repository.PublicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * Service for managing publication approval workflow.
 * Implements university governance rules:
 * - STUDENT submissions require mentor (FACULTY) approval first
 * - FACULTY submissions can be directly approved by HOD/ADMIN
 * - APPROVED publications cannot be edited
 */
@Service
@RequiredArgsConstructor
@Transactional
public class ApprovalService {

    private final PublicationRepository publicationRepository;

    /**
     * Submit a publication for approval
     * @param publication The publication to submit
     * @param submitter The user submitting the publication
     * @throws IllegalStateException if publication already submitted/approved
     */
    public void submitForApproval(Publication publication, User submitter) {
        if (publication.getStatus() != Publication.Status.PENDING) {
            throw new IllegalStateException("Publication has already been submitted for approval");
        }

        publication.setSubmittedBy(submitter);
        publication.setSubmittedAt(LocalDateTime.now());
        publication.setStatus(Publication.Status.PENDING);
        
        publicationRepository.save(publication);
    }

    /**
     * Approve a publication
     * @param publicationId The publication ID to approve
     * @param approver The user approving the publication
     * @param remarks Optional remarks/comments
     * @throws IllegalArgumentException if publication not found
     * @throws IllegalStateException if publication not in valid state for approval
     * @throws SecurityException if approver doesn't have permission
     */
    public void approvePublication(Long publicationId, User approver, String remarks) {
        Publication publication = publicationRepository.findById(publicationId)
                .orElseThrow(() -> new IllegalArgumentException("Publication not found"));

        // Validate state
        if (publication.getStatus() != Publication.Status.PENDING) {
            throw new IllegalStateException("Only PENDING publications can be approved");
        }

        // Validate permissions
        validateApprovalPermission(publication, approver);

        // Approve
        publication.setApprovedBy(approver);
        publication.setApprovedAt(LocalDateTime.now());
        publication.setRemarks(remarks);
        publication.setStatus(Publication.Status.APPROVED);

        publicationRepository.save(publication);
    }

    /**
     * Reject a publication
     * @param publicationId The publication ID to reject
     * @param rejector The user rejecting the publication
     * @param remarks Rejection reason (required)
     * @throws IllegalArgumentException if publication not found or remarks missing
     * @throws IllegalStateException if publication not in valid state for rejection
     * @throws SecurityException if rejector doesn't have permission
     */
    public void rejectPublication(Long publicationId, User rejector, String remarks) {
        if (remarks == null || remarks.trim().isEmpty()) {
            throw new IllegalArgumentException("Remarks are required when rejecting a publication");
        }

        Publication publication = publicationRepository.findById(publicationId)
                .orElseThrow(() -> new IllegalArgumentException("Publication not found"));

        // Validate state
        if (publication.getStatus() != Publication.Status.PENDING) {
            throw new IllegalStateException("Only PENDING publications can be rejected");
        }

        // Validate permissions
        validateApprovalPermission(publication, rejector);

        // Reject
        publication.setRejectedBy(rejector);
        publication.setRejectedAt(LocalDateTime.now());
        publication.setRemarks(remarks);
        publication.setStatus(Publication.Status.REJECTED);

        publicationRepository.save(publication);
    }

    /**
     * Check if a user can approve/reject a publication
     * Business rules:
     * - ADMIN can approve/reject anything
     * - HOD can approve/reject anything in their department
     * - FACULTY can approve/reject their mentees' STUDENT submissions
     * - STUDENT cannot approve/reject
     */
    private void validateApprovalPermission(Publication publication, User approver) {
        User submitter = publication.getSubmittedBy() != null ? 
                publication.getSubmittedBy() : publication.getAddedBy();

        switch (approver.getRole()) {
            case ADMIN:
                // ADMIN can approve anything
                return;
                
            case HOD:
                // HOD can approve anything in their department
                if (submitter != null && !approver.getDepartment().equals(submitter.getDepartment())) {
                    throw new SecurityException("HOD can only approve publications from their department");
                }
                return;
                
            case FACULTY:
                // FACULTY can approve their mentees' submissions
                if (submitter == null || submitter.getRole() != User.Role.STUDENT) {
                    throw new SecurityException("Faculty can only approve student submissions");
                }
                if (submitter.getMentor() == null || !submitter.getMentor().getId().equals(approver.getId())) {
                    throw new SecurityException("Faculty can only approve their own mentees' publications");
                }
                return;
                
            case STUDENT:
                throw new SecurityException("Students cannot approve publications");
                
            default:
                throw new SecurityException("Invalid role for approval");
        }
    }

    /**
     * Check if a publication can be edited
     * @param publication The publication to check
     * @return true if editable, false if locked
     */
    public boolean canEdit(Publication publication) {
        // APPROVED publications cannot be edited (locked for governance)
        return publication.getStatus() != Publication.Status.APPROVED;
    }

    /**
     * Check if a user can edit a specific publication
     * @param publication The publication to edit
     * @param user The user attempting to edit
     * @return true if user has permission to edit
     */
    public boolean canUserEdit(Publication publication, User user) {
        // Cannot edit if approved
        if (!canEdit(publication)) {
            return false;
        }

        // ADMIN can edit anything
        if (user.getRole() == User.Role.ADMIN) {
            return true;
        }

        // Owner can edit their own pending/rejected publications
        User owner = publication.getAddedBy();
        if (owner != null && owner.getId().equals(user.getId())) {
            return true;
        }

        // HOD can edit publications in their department
        if (user.getRole() == User.Role.HOD && owner != null && 
            user.getDepartment().equals(owner.getDepartment())) {
            return true;
        }

        return false;
    }

    /**
     * Get pending publications for a specific approver
     * This would be used to show "Pending Approvals" on dashboards
     */
    public java.util.List<Publication> getPendingApprovalsFor(User approver) {
        switch (approver.getRole()) {
            case ADMIN:
                return publicationRepository.findByStatus(Publication.Status.PENDING);
                
            case HOD:
                return publicationRepository.findByStatusAndAddedByDepartment(
                        Publication.Status.PENDING, approver.getDepartment());
                
            case FACULTY:
                // Get pending publications from mentees
                return publicationRepository.findByStatusAndAddedByMentorId(
                        Publication.Status.PENDING, approver.getId());
                
            default:
                return java.util.Collections.emptyList();
        }
    }
}
