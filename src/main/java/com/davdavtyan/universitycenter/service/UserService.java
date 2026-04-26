package com.davdavtyan.universitycenter.service;

import com.davdavtyan.universitycenter.UserRepository;
import com.davdavtyan.universitycenter.entity.Role;
import com.davdavtyan.universitycenter.entity.User;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public User updateUser(Long id, User user) {
        return userRepository.findById(id)
            .map(userDb -> {
                userDb.setStatus(user.getStatus());
                userDb.setName(user.getName());
                userDb.setLastName(user.getLastName());
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

}
