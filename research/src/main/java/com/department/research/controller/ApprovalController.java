package com.department.research.controller;

import com.department.research.model.Publication;
import com.department.research.model.User;
import com.department.research.service.ApprovalService;
import com.department.research.service.PublicationService;
import com.department.research.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * Controller for handling publication approval workflow
 */
@Controller
@RequestMapping("/approvals")
@RequiredArgsConstructor
public class ApprovalController {

    private final ApprovalService approvalService;
    private final PublicationService publicationService;
    private final UserService userService;

    /**
     * Display pending approvals for the logged-in user
     */
    @GetMapping
    public String listPendingApprovals(Model model, Authentication authentication) {
        User currentUser = userService.getUserByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Publication> pendingApprovals = approvalService.getPendingApprovalsFor(currentUser);
        
        model.addAttribute("pendingApprovals", pendingApprovals);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("pageTitle", "Pending Approvals");
        
        return "approvals/list";
    }

    /**
     * Display approval details for a specific publication
     */
    @GetMapping("/{id}")
    public String viewApprovalDetails(@PathVariable Long id, Model model, Authentication authentication) {
        Publication publication = publicationService.getPublicationById(id)
                .orElseThrow(() -> new RuntimeException("Publication not found"));

        User currentUser = userService.getUserByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        model.addAttribute("publication", publication);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("canApprove", canUserApprove(publication, currentUser));
        model.addAttribute("pageTitle", "Approval Details");
        
        return "approvals/details";
    }

    /**
     * Approve a publication
     */
    @PostMapping("/{id}/approve")
    public String approvePublication(
            @PathVariable Long id,
            @RequestParam(required = false) String remarks,
            Authentication authentication,
            RedirectAttributes redirectAttributes) {
        
        try {
            User currentUser = userService.getUserByEmail(authentication.getName())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            approvalService.approvePublication(id, currentUser, remarks);
            
            redirectAttributes.addFlashAttribute("successMessage", 
                "Publication approved successfully!");
            
        } catch (IllegalArgumentException | IllegalStateException | SecurityException e) {
            redirectAttributes.addFlashAttribute("errorMessage", 
                "Error: " + e.getMessage());
        }
        
        return "redirect:/approvals";
    }

    /**
     * Reject a publication
     */
    @PostMapping("/{id}/reject")
    public String rejectPublication(
            @PathVariable Long id,
            @RequestParam String remarks,
            Authentication authentication,
            RedirectAttributes redirectAttributes) {
        
        try {
            if (remarks == null || remarks.trim().isEmpty()) {
                throw new IllegalArgumentException("Remarks are required when rejecting");
            }

            User currentUser = userService.getUserByEmail(authentication.getName())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            approvalService.rejectPublication(id, currentUser, remarks);
            
            redirectAttributes.addFlashAttribute("successMessage", 
                "Publication rejected successfully!");
            
        } catch (IllegalArgumentException | IllegalStateException | SecurityException e) {
            redirectAttributes.addFlashAttribute("errorMessage", 
                "Error: " + e.getMessage());
        }
        
        return "redirect:/approvals";
    }

    /**
     * Check if current user can approve a publication
     */
    private boolean canUserApprove(Publication publication, User user) {
        try {
            // This is a read-only check, won't modify data
            // We just need to see if validation passes
            switch (user.getRole()) {
                case ADMIN:
                    return true;
                case HOD:
                    User submitter = publication.getSubmittedBy() != null ? 
                            publication.getSubmittedBy() : publication.getAddedBy();
                    return submitter != null && 
                           user.getDepartment().equals(submitter.getDepartment());
                case FACULTY:
                    submitter = publication.getSubmittedBy() != null ? 
                            publication.getSubmittedBy() : publication.getAddedBy();
                    return submitter != null && 
                           submitter.getRole() == User.Role.STUDENT &&
                           submitter.getMentor() != null &&
                           submitter.getMentor().getId().equals(user.getId());
                default:
                    return false;
            }
        } catch (Exception e) {
            return false;
        }
    }
}
