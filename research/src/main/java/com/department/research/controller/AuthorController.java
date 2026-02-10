package com.department.research.controller;

import com.department.research.model.Author;
import com.department.research.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/authors")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping
    public String listAuthors(Model model) {
        List<Author> authors = authorService.getAllAuthors();
        model.addAttribute("authors", authors);
        return "authors/list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("author", new Author());
        model.addAttribute("authorTypes", Author.AuthorType.values());
        return "authors/add";
    }

    @PostMapping("/add")
    public String addAuthor(@Valid @ModelAttribute Author author, 
                           BindingResult result,
                           RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "authors/add";
        }
        
        try {
            authorService.createAuthor(author);
            redirectAttributes.addFlashAttribute("success", "Author added successfully!");
            return "redirect:/authors";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error adding author: " + e.getMessage());
            return "redirect:/authors/add";
        }
    }

    @GetMapping("/{id}")
    public String viewAuthor(@PathVariable Long id, Model model) {
        Author author = authorService.getAuthorById(id)
                .orElseThrow(() -> new RuntimeException("Author not found"));
        model.addAttribute("author", author);
        return "authors/view";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        Author author = authorService.getAuthorById(id)
                .orElseThrow(() -> new RuntimeException("Author not found"));
        model.addAttribute("author", author);
        model.addAttribute("authorTypes", Author.AuthorType.values());
        return "authors/edit";
    }

    @PostMapping("/{id}/edit")
    public String updateAuthor(@PathVariable Long id,
                              @Valid @ModelAttribute Author author,
                              BindingResult result,
                              RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "authors/edit";
        }
        
        try {
            authorService.updateAuthor(id, author);
            redirectAttributes.addFlashAttribute("success", "Author updated successfully!");
            return "redirect:/authors/" + id;
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error updating author: " + e.getMessage());
            return "redirect:/authors/" + id + "/edit";
        }
    }

    @PostMapping("/{id}/delete")
    public String deleteAuthor(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            authorService.deleteAuthor(id);
            redirectAttributes.addFlashAttribute("success", "Author deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error deleting author: " + e.getMessage());
        }
        return "redirect:/authors";
    }

    @GetMapping("/search")
    public String searchAuthors(@RequestParam String query, Model model) {
        List<Author> authors = authorService.searchAuthors(query);
        model.addAttribute("authors", authors);
        model.addAttribute("searchQuery", query);
        return "authors/list";
    }
}
