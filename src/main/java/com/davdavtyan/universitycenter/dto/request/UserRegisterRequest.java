package com.davdavtyan.universitycenter.dto.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserRegisterRequest {
    private String password;
    private String name;
    private String lastName;
    private String username;
    private String email;
    private String role;

}
