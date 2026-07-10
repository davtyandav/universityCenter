package com.davdavtyan.universitycenter.controller;

import com.davdavtyan.universitycenter.converter.UserConverter;
import com.davdavtyan.universitycenter.dto.request.PasswordUpdateRequest;
import com.davdavtyan.universitycenter.dto.request.UserRequest;
import com.davdavtyan.universitycenter.dto.response.UserResponse;
import com.davdavtyan.universitycenter.dto.response.PaginatedUserResponse; // Import new wrapper
import com.davdavtyan.universitycenter.entity.Role;
import com.davdavtyan.universitycenter.entity.User;
import com.davdavtyan.universitycenter.service.UserService;
import java.util.List;
import org.springframework.data.domain.Page; // Import Page utilities
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam; // Import RequestParam
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
    public ResponseEntity<PaginatedUserResponse> getUsers(
        @RequestParam(defaultValue = "1") int page,
        @RequestParam(defaultValue = "5") int limit,
        @RequestParam(defaultValue = "") String search) {

        Pageable pageable = PageRequest.of(page - 1, limit);

        Page<User> userPage = userService.getUsersPaginatedAndFiltered(search, pageable);

        List<UserResponse> userResponses = userPage.getContent()
            .stream()
            .map(UserConverter::toDto)
            .toList();

        PaginatedUserResponse response = new PaginatedUserResponse(userResponses, userPage.getTotalPages());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/status/{id}")
    public ResponseEntity<UserResponse> updateUserStatus(@PathVariable Long id,
                                                         @RequestBody UserRequest userRequest) {
        try {
            User updateUser = userService.updateUser(id, userRequest.getStatus());
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

    @PutMapping("/{id}/password")
    public ResponseEntity<?> updateUserPassword(@PathVariable Long id,
                                                @RequestBody PasswordUpdateRequest passwordRequest) {
        try {
            userService.updatePassword(id, passwordRequest.getOldPassword(), passwordRequest.getNewPassword());
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
