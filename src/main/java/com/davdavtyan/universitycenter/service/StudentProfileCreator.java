package com.davdavtyan.universitycenter.service;

import com.davdavtyan.universitycenter.StudentRepository;
import com.davdavtyan.universitycenter.dto.request.UserRegisterRequest;
import com.davdavtyan.universitycenter.entity.Role;
import com.davdavtyan.universitycenter.entity.Student;
import com.davdavtyan.universitycenter.entity.User;
import org.springframework.stereotype.Component;

@Component
public class StudentProfileCreator implements ProfileCreator {

    private final StudentRepository studentRepository;
    public StudentProfileCreator(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public boolean supports(Role role) {
        return role == Role.STUDENT;
    }

    @Override
    public void createProfile(User user, UserRegisterRequest request) {
        Student student = new Student();
        student.setUser(user);
        studentRepository.save(student);
    }

}