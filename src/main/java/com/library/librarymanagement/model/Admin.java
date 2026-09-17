package com.library.librarymanagement.model;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Admin extends User {
    
    @Override
    public String getRole() {
        return "ADMIN";
    }
}