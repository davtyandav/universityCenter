package com.davdavtyan.universitycenter.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordUpdateRequest {

    private String oldPassword;
    private String newPassword;

}