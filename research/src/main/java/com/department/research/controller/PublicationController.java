package com.department.research.controller;

import com.department.research.model.Author;
import com.department.research.model.Publication;
import com.department.research.model.User;
import com.department.research.service.AuthorService;
import com.department.research.service.PublicationService;
import com.department.research.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/publications")
@RequiredArgsConstructor
public class PublicationController {

    private final PublicationService publicationService;
    private final AuthorService authorService;
    private final UserService userService;

    @GetMapping
    public String listPublications(
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String indexType,
            @RequestParam(required = false) String status,
            Model model) {
        
        List<Publication> publications;
        
        Publication.PublicationType pubType = type != null ? Publication.PublicationType.valueOf(type) : null;
        Publication.IndexType idx = indexType != null ? Publication.IndexType.valueOf(indexType) : null;
        Publication.Status stat = status != null ? Publication.Status.valueOf(status) : null;
        
        if (year != null || pubType != null || idx != null || stat != null) {
            publications = publicationService.filterPublications(year, pubType, idx, stat);
        } else {
            publications = publicationService.getAllPublications();
        }
        
        model.addAttribute("publications", publications);
        model.addAttribute("publicationTypes", Publication.PublicationType.values());
        model.addAttribute("indexTypes", Publication.IndexType.values());
        model.addAttribute("statuses", Publication.Status.values());
        
        return "publications/list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("publication", new Publication());
        model.addAttribute("authors", authorService.getAllAuthors());
        model.addAttribute("publicationTypes", Publication.PublicationType.values());
        model.addAttribute("indexTypes", Publication.IndexType.values());
        return "publications/add";
    }

    @PostMapping("/add")
    public String addPublication(
            @Valid @ModelAttribute Publication publication,
            @RequestParam(required = false) List<Long> authorIds,
            @AuthenticationPrincipal UserDetails userDetails,
            RedirectAttributes redirectAttributes) {
        
        try {
            User currentUser = userService.getUserByEmail(userDetails.getUsername())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            
            publicationService.createPublication(publication, authorIds, currentUser);
            redirectAttributes.addFlashAttribute("success", "Publication added successfully!");
            return "redirect:/publications";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error adding publication: " + e.getMessage());
            return "redirect:/publications/add";
        }
    }

    @GetMapping("/{id}")
    public String viewPublication(@PathVariable Long id, Model model) {
        Publication publication = publicationService.getPublicationById(id)
                .orElseThrow(() -> new RuntimeException("Publication not found"));
        model.addAttribute("publication", publication);
        return "publications/view";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        Publication publication = publicationService.getPublicationById(id)
                .orElseThrow(() -> new RuntimeException("Publication not found"));
        
        model.addAttribute("publication", publication);
        model.addAttribute("authors", authorService.getAllAuthors());
        model.addAttribute("publicationTypes", Publication.PublicationType.values());
        model.addAttribute("indexTypes", Publication.IndexType.values());
        model.addAttribute("selectedAuthorIds", 
            publication.getAuthors().stream().map(Author::getId).collect(Collectors.toList()));
        
        return "publications/edit";
    }

    @PostMapping("/{id}/edit")
    public String updatePublication(
            @PathVariable Long id,
            @Valid @ModelAttribute Publication publication,
            @RequestParam(required = false) List<Long> authorIds,
            RedirectAttributes redirectAttributes) {
        
        try {
            publicationService.updatePublication(id, publication, authorIds);
            redirectAttributes.addFlashAttribute("success", "Publication updated successfully!");
            return "redirect:/publications/" + id;
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error updating publication: " + e.getMessage());
            return "redirect:/publications/" + id + "/edit";
        }
    }

    @PostMapping("/{id}/delete")
    public String deletePublication(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            publicationService.deletePublication(id);
            redirectAttributes.addFlashAttribute("success", "Publication deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error deleting publication: " + e.getMessage());
        }
        return "redirect:/publications";
    }

    @PostMapping("/{id}/approve")
    public String approvePublication(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            publicationService.approvePublication(id);
            redirectAttributes.addFlashAttribute("success", "Publication approved successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error approving publication: " + e.getMessage());
        }
        return "redirect:/publications/" + id;
    }

    @PostMapping("/{id}/reject")
    public String rejectPublication(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            publicationService.rejectPublication(id);
            redirectAttributes.addFlashAttribute("success", "Publication rejected!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error rejecting publication: " + e.getMessage());
        }
        return "redirect:/publications/" + id;
    }

    @GetMapping("/search")
    public String searchPublications(@RequestParam String query, Model model) {
        List<Publication> publications = publicationService.searchPublications(query);
        model.addAttribute("publications", publications);
        model.addAttribute("searchQuery", query);
        return "publications/list";
    }
}
