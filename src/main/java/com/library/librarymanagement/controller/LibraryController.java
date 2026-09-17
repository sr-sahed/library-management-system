package com.library.librarymanagement.controller;

import com.library.librarymanagement.model.Book;
import com.library.librarymanagement.model.User;
import com.library.librarymanagement.service.LibraryService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class LibraryController {

    private final LibraryService libraryService;

    public LibraryController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    // Show Login Page
    @GetMapping("/")
    public String loginPage() {
        return "login";
    }

    // Handle Login Authentication
    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, HttpSession session, Model model) {
        User user = libraryService.authenticate(username, password);
        if (user != null) {
            session.setAttribute("loggedUser", user);
            if ("ADMIN".equals(user.getRole())) {
                return "redirect:/admin-dashboard";
            } else {
                return "redirect:/user-dashboard";
            }
        }
        model.addAttribute("error", "Invalid Username or Password");
        return "login";
    }

    // Admin Dashboard
    @GetMapping("/admin-dashboard")
    public String adminDashboard(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedUser");
        if (user == null || !"ADMIN".equals(user.getRole())) {
            return "redirect:/";
        }
        model.addAttribute("books", libraryService.getAllBooks());
        model.addAttribute("book", new Book());
        model.addAttribute("borrowRecords", libraryService.getAllBorrowRecords());
        return "admin-dashboard";
    }

    // Add Book by Admin
    @PostMapping("/admin/add-book")
    public String addBook(@ModelAttribute Book book, HttpSession session) {
        User user = (User) session.getAttribute("loggedUser");
        if (user == null || !"ADMIN".equals(user.getRole())) {
            return "redirect:/";
        }
        libraryService.saveBook(book);
        return "redirect:/admin-dashboard";
    }

    // User Dashboard
    @GetMapping("/user-dashboard")
    public String userDashboard(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedUser");
        if (user == null || !"USER".equals(user.getRole())) {
            return "redirect:/";
        }
        model.addAttribute("books", libraryService.getAllBooks());
        model.addAttribute("myRecords", libraryService.getUserBorrowRecords(user.getUsername()));
        model.addAttribute("username", user.getUsername());
        return "user-dashboard";
    }

    // Borrow Book by User
    @GetMapping("/user/borrow/{id}")
    public String borrowBook(@PathVariable Long id, HttpSession session) {
        User user = (User) session.getAttribute("loggedUser");
        if (user != null && "USER".equals(user.getRole())) {
            libraryService.borrowBook(id, user.getUsername());
        }
        return "redirect:/user-dashboard";
    }

    // Return Book
    @GetMapping("/return/{id}")
    public String returnBook(@PathVariable Long id, HttpSession session) {
        User user = (User) session.getAttribute("loggedUser");
        if (user != null) {
            libraryService.returnBook(id);
            if ("ADMIN".equals(user.getRole())) {
                return "redirect:/admin-dashboard";
            } else {
                return "redirect:/user-dashboard";
            }
        }
        return "redirect:/";
    }

    // Search books endpoint for users/admin
    @GetMapping("/search")
    public String searchBooks(@RequestParam("keyword") String keyword, HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedUser");
        if (user == null) {
            return "redirect:/";
        }
        
        model.addAttribute("books", libraryService.searchBooks(keyword));
        
        if ("ADMIN".equals(user.getRole())) {
            model.addAttribute("book", new Book());
            model.addAttribute("borrowRecords", libraryService.getAllBorrowRecords());
            return "admin-dashboard";
        } else {
            model.addAttribute("username", user.getUsername());
            model.addAttribute("myRecords", libraryService.getUserBorrowRecords(user.getUsername()));
            return "user-dashboard";
        }
    }

    // Logout
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}