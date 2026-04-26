package com.davdavtyan.universitycenter.controller;

import com.davdavtyan.universitycenter.converter.MentorConverter;
import com.davdavtyan.universitycenter.converter.UserConverter;
import com.davdavtyan.universitycenter.dto.request.MentorRequest;
import com.davdavtyan.universitycenter.dto.request.UserRequest;
import com.davdavtyan.universitycenter.dto.response.MentorResponse;
import com.davdavtyan.universitycenter.dto.response.UserResponse;
import com.davdavtyan.universitycenter.entity.Mentor;
import com.davdavtyan.universitycenter.entity.Role;
import com.davdavtyan.universitycenter.entity.User;
import com.davdavtyan.universitycenter.service.UserService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/role/{role}")
    public ResponseEntity<List<UserResponse>> getUsersByRole(@PathVariable Role role) {
        List<UserResponse> users = userService.getUsersByRole(role)
            .stream()
            .map(UserConverter::toDto)
            .toList();

        return ResponseEntity.ok(users);
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getUsers() {
        List<UserResponse> users = userService.getUsers()
            .stream()
            .map(UserConverter::toDto)
            .toList();

        return ResponseEntity.ok(users);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Long id,
                                                       @RequestBody UserRequest userRequest) {
        try {

            User entity = UserConverter.toEntity(userRequest);
            User updateUser = userService.updateUser(id, entity);
            UserResponse dto = UserConverter.toDto(updateUser);
            return ResponseEntity.ok(dto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/auth/me")
    public ResponseEntity<UserResponse> getMyProfile(Authentication authentication) {
        String currentUserEmail = authentication.getName();

        UserResponse profile = userService.getFullProfile(currentUserEmail)
            .map(UserConverter::toDto)
            .orElseThrow(() -> new RuntimeException("Not found user"));

        return ResponseEntity.ok(profile);
    }

}
