package com.library.librarymanagement;

import com.library.librarymanagement.model.Admin;
import com.library.librarymanagement.model.Student;
import com.library.librarymanagement.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initData(UserRepository userRepository) {
        return args -> {
            // Check if users already exist, if not create default admin and student
            if (userRepository.count() == 0) {
                Admin admin = new Admin();
                admin.setUsername("admin");
                admin.setPassword("1234");
                userRepository.save(admin);

                Student student = new Student();
                student.setUsername("student");
                student.setPassword("1234");
                userRepository.save(student);
            }
        };
    }
}