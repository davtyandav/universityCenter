package com.davdavtyan.universitycenter.controller;

import com.davdavtyan.universitycenter.converter.UserConverter;
import com.davdavtyan.universitycenter.dto.response.UserResponse;
import com.davdavtyan.universitycenter.entity.Role;
import com.davdavtyan.universitycenter.service.UserService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

}
