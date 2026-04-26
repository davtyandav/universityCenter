package com.davdavtyan.universitycenter.dto.request;

import com.davdavtyan.universitycenter.entity.Status;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserRequest {
    private Long id;
    private String name;
    private String lastName;
    private String username;
    private String email;
    private Status status;
}
