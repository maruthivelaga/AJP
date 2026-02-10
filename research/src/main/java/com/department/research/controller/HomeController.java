package com.department.research.controller;

import com.department.research.model.Publication;
import com.department.research.model.User;
import com.department.research.service.PublicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final PublicationService publicationService;

    @GetMapping("/")
    public String home(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        // Dashboard statistics
        model.addAttribute("totalPublications", publicationService.getTotalPublications());
        model.addAttribute("approvedPublications", publicationService.getApprovedPublications());
        model.addAttribute("pendingPublications", publicationService.getPendingPublications());
        
        // Year-wise publication counts
        Map<Integer, Long> yearWiseCounts = publicationService.getPublicationCountByYear();
        model.addAttribute("yearWiseCounts", yearWiseCounts);
        
        // Type-wise publication counts
        Map<String, Long> typeWiseCounts = publicationService.getPublicationCountByType();
        model.addAttribute("typeWiseCounts", typeWiseCounts);
        
        // Recent publications
        model.addAttribute("recentPublications", publicationService.getAllPublications());
        
        if (userDetails != null) {
            model.addAttribute("username", userDetails.getUsername());
        }
        
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/access-denied")
    public String accessDenied() {
        return "access-denied";
    }
}
