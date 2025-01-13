package com.davdavtyan.universitycenter.controller;

import com.davdavtyan.universitycenter.dto.request.RoleResponse;
import com.davdavtyan.universitycenter.entity.Role;
import java.util.Arrays;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/roles")
@CrossOrigin(origins = "http://localhost:3000")
public class RoleController {

    @GetMapping
    public ResponseEntity<List<RoleResponse>> getAllRoles() {
        List<RoleResponse> roles = Arrays.stream(Role.values())
            .map(role -> {
                RoleResponse roleResponse = new RoleResponse();
                roleResponse.setLabel(role.name());
                roleResponse.setValue(capitalize(role.name().toLowerCase()));
                return roleResponse;
            })
            .toList();

        return ResponseEntity.ok(roles);
    }

    private String capitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

}