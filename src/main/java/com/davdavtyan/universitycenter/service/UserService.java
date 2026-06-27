package com.davdavtyan.universitycenter.service;

import com.davdavtyan.universitycenter.UserRepository;
import com.davdavtyan.universitycenter.entity.Role;
import com.davdavtyan.universitycenter.entity.Status;
import com.davdavtyan.universitycenter.entity.User;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public User updateUser(Long id, String status) {
        return userRepository.findById(id)
            .map(userDb -> {
                userDb.setStatus(Status.valueOf(status));
                userDb.setUpdatedAt(LocalDateTime.now());
                return userRepository.save(userDb);
            })
            .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + id));
    }
    public List<User> getUsersByRole(Role role) {
        return userRepository.findAllByRole(role);
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getFullProfile(String currentUserEmail) {
        return userRepository.findByEmail(currentUserEmail);
    }

    public void updatePassword(Long id, String oldPassword, String newPassword) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Пользователь не найден"));

        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new IllegalArgumentException("Неверный текущий пароль");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        user.setUpdatedAt(LocalDateTime.now());

        userRepository.save(user);
    }

    public Page<User> getUsersPaginatedAndFiltered(String search, Pageable pageable) {
        if (search == null || search.trim().isEmpty()) {
            return userRepository.findAll(pageable);
        }
        return userRepository.findByNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrEmailContainingIgnoreCase(
            search, search, search, pageable
        );
    }

}
