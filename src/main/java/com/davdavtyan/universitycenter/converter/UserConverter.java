package com.davdavtyan.universitycenter.converter;

import com.davdavtyan.universitycenter.dto.request.UserRequest;
import com.davdavtyan.universitycenter.dto.response.UserResponse;
import com.davdavtyan.universitycenter.entity.User;

public class UserConverter {

    public static UserResponse toDto(User user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setName(user.getName());
        userResponse.setLastName(user.getLastName());
        userResponse.setUsername(user.getUsername());
        userResponse.setEmail(user.getEmail());
        userResponse.setStatus(user.getStatus() == null ? "" : user.getStatus().name());
        userResponse.setRole(user.getRole() == null ? "" : user.getRole().name());
        return userResponse;
    }

    public static User toEntity(UserRequest userRequest) {
        User user = new User();
        user.setId(userRequest.getId());
        user.setName(userRequest.getName());
        user.setLastName(userRequest.getLastName());
        user.setUsername(userRequest.getUsername());
        user.setEmail(userRequest.getEmail());
        return user;
    }

}
