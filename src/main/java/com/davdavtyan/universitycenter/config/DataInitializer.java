package com.davdavtyan.universitycenter.config;

import com.davdavtyan.universitycenter.UserRepository;
import com.davdavtyan.universitycenter.entity.User;
import com.davdavtyan.universitycenter.entity.Role;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    private final AppAdminProperties adminProperties;

    public DataInitializer(AppAdminProperties adminProperties) {
        this.adminProperties = adminProperties;
    }

    @Bean
    public CommandLineRunner initData(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        String adminEmail = adminProperties.getEmail();
        String adminPassword = adminProperties.getPassword();

        return args -> {
            if (userRepository.findByEmail(adminEmail).isEmpty()) {
                User admin = new User();
                admin.setName("System");
                admin.setLastName("Admin");
                admin.setEmail(adminEmail);
                admin.setRole(Role.ADMIN);

                admin.setPassword(passwordEncoder.encode(adminPassword));

                userRepository.save(admin);

                System.out.println(">>> Default Admin created: " + adminEmail);
            } else {
                System.out.println(">>> Admin already exists, skipping initialization.");
            }
        };
    }

}
