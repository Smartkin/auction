package com.badcompany.auction.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/role")
public class RoleController {
    @GetMapping("/all")
    public String allAccess() {
        return "Public content.";
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER') or hasRole('EVALUATOR') or hasRole('ADMIN')")
    public String userAccess() {
        return "User content.";
    }

    @GetMapping("/evaluator")
    @PreAuthorize("hasRole('EVALUATOR') or hasRole('ADMIN')")
    public String evaluatorAccess() {
        return "Evaluator dashboard.";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminAccess() {
        return "Admin dashboard.";
    }
}
