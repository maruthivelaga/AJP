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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;
    private final PublicationService publicationService;
    private final ApprovalService approvalService;

    @GetMapping("/users")
    public String listUsers(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "admin/users";
    }

    @GetMapping("/users/add")
    public String showAddUserForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", User.Role.values());
        return "admin/add-user";
    }

    @PostMapping("/users/add")
    public String addUser(@Valid @ModelAttribute User user,
                         BindingResult result,
                         RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "admin/add-user";
        }
        
        try {
            userService.createUser(user);
            redirectAttributes.addFlashAttribute("success", "User added successfully!");
            return "redirect:/admin/users";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error adding user: " + e.getMessage());
            return "redirect:/admin/users/add";
        }
    }

    @GetMapping("/users/{id}/edit")
    public String showEditUserForm(@PathVariable Long id, Model model) {
        User user = userService.getUserById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        model.addAttribute("user", user);
        model.addAttribute("roles", User.Role.values());
        return "admin/edit-user";
    }

    @PostMapping("/users/{id}/edit")
    public String updateUser(@PathVariable Long id,
                            @Valid @ModelAttribute User user,
                            BindingResult result,
                            RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "admin/edit-user";
        }
        
        try {
            userService.updateUser(id, user);
            redirectAttributes.addFlashAttribute("success", "User updated successfully!");
            return "redirect:/admin/users";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error updating user: " + e.getMessage());
            return "redirect:/admin/users/" + id + "/edit";
        }
    }

    @PostMapping("/users/{id}/delete")
    public String deleteUser(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            userService.deleteUser(id);
            redirectAttributes.addFlashAttribute("success", "User deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error deleting user: " + e.getMessage());
        }
        return "redirect:/admin/users";
    }

    // Admin Dashboard
    @GetMapping
    public String adminDashboard(Model model) {
        List<Publication> pendingPublications = publicationService.getPublicationsByStatus(Publication.Status.PENDING);
        List<Publication> allPublications = publicationService.getAllPublications();
        List<User> allUsers = userService.getAllUsers();

        model.addAttribute("pendingPublications", pendingPublications);
        model.addAttribute("pendingCount", pendingPublications.size());
        model.addAttribute("totalPublications", allPublications.size());
        model.addAttribute("totalUsers", allUsers.size());
        model.addAttribute("approvedCount", allPublications.stream()
                .filter(p -> p.getStatus() == Publication.Status.APPROVED).count());
        model.addAttribute("rejectedCount", allPublications.stream()
                .filter(p -> p.getStatus() == Publication.Status.REJECTED).count());
        
        return "admin/dashboard";
    }

    // Publication Management
    @GetMapping("/publications")
    public String listAllPublications(Model model) {
        List<Publication> publications = publicationService.getAllPublications();
        model.addAttribute("publications", publications);
        return "admin/publications";
    }

    @GetMapping("/publications/pending")
    public String listPendingPublications(Model model) {
        List<Publication> pendingPublications = publicationService.getPublicationsByStatus(Publication.Status.PENDING);
        model.addAttribute("publications", pendingPublications);
        model.addAttribute("pageTitle", "Pending Publications");
        return "admin/pending-publications";
    }

    @PostMapping("/publications/{id}/approve")
    public String approvePublication(@PathVariable Long id,
                                    @RequestParam(required = false) String remarks,
                                    Authentication authentication,
                                    RedirectAttributes redirectAttributes) {
        try {
            User admin = userService.getUserByEmail(authentication.getName())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            
            approvalService.approvePublication(id, admin, remarks);
            redirectAttributes.addFlashAttribute("success", "Publication approved successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error: " + e.getMessage());
        }
        return "redirect:/admin/publications/pending";
    }

    @PostMapping("/publications/{id}/reject")
    public String rejectPublication(@PathVariable Long id,
                                   @RequestParam String remarks,
                                   Authentication authentication,
                                   RedirectAttributes redirectAttributes) {
        try {
            if (remarks == null || remarks.trim().isEmpty()) {
                throw new IllegalArgumentException("Remarks are required when rejecting");
            }

            User admin = userService.getUserByEmail(authentication.getName())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            
            approvalService.rejectPublication(id, admin, remarks);
            redirectAttributes.addFlashAttribute("success", "Publication rejected successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error: " + e.getMessage());
        }
        return "redirect:/admin/publications/pending";
    }

    @GetMapping("/publications/{id}")
    public String viewPublication(@PathVariable Long id, Model model) {
        Publication publication = publicationService.getPublicationById(id)
                .orElseThrow(() -> new RuntimeException("Publication not found"));
        model.addAttribute("publication", publication);
        return "admin/publication-details";
    }
}
