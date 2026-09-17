package com.library.librarymanagement.model;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Student extends User {
    
    @Override
    public String getRole() {
        return "USER";
    }
}