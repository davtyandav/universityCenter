package com.davdavtyan.universitycenter.service;

import com.davdavtyan.universitycenter.dto.request.UserRegisterRequest;
import com.davdavtyan.universitycenter.entity.Role;
import com.davdavtyan.universitycenter.entity.User;

public interface ProfileCreator {

    boolean supports(Role role);

    void createProfile(User user, UserRegisterRequest request);

}
