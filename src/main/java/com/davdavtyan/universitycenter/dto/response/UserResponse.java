package com.davdavtyan.universitycenter.dto.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserResponse {

    private Long id;
    private String name;
    private String lastName;
    private String username;
    private String email;
    private String status;
    private String role;

}
