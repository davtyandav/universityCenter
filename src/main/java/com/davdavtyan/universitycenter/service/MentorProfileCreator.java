package com.davdavtyan.universitycenter.service;

import com.davdavtyan.universitycenter.MentorRepository;
import com.davdavtyan.universitycenter.dto.request.UserRegisterRequest;
import com.davdavtyan.universitycenter.entity.Mentor;
import com.davdavtyan.universitycenter.entity.Role;
import com.davdavtyan.universitycenter.entity.User;
import org.springframework.stereotype.Component;

@Component
public class MentorProfileCreator implements ProfileCreator {

    private final MentorRepository mentorRepository;
    public MentorProfileCreator(MentorRepository mentorRepository) {
        this.mentorRepository = mentorRepository;
    }

    @Override
    public boolean supports(Role role) { return role == Role.MENTOR; }

    @Override
    public void createProfile(User user, UserRegisterRequest request) {
        Mentor mentor = new Mentor();
        mentor.setUser(user);
        mentorRepository.save(mentor);
    }
}