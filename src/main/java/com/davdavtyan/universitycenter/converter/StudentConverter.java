package com.davdavtyan.universitycenter.converter;

import com.davdavtyan.universitycenter.dto.request.MentorRequest;
import com.davdavtyan.universitycenter.dto.request.StudentRequest;
import com.davdavtyan.universitycenter.dto.response.StudentResponse;
import com.davdavtyan.universitycenter.entity.Student;

public class StudentConverter {

    public static Student toEntity(StudentRequest studentRequest) {
        Student student = new Student();
        student.setId(studentRequest.getId());
        student.setName(studentRequest.getName());
        student.setLastName(studentRequest.getLastName());
        student.setBirthDate(studentRequest.getBirthDate());
        student.setEmail(studentRequest.getEmail());

        return student;
    }


    public static StudentResponse toDto(Student studentDto) {
        StudentResponse studentResponse = new StudentResponse();
        studentResponse.setId(studentDto.getId());
        studentResponse.setName(studentDto.getName());
        studentResponse.setLastName(studentDto.getLastName());
        studentResponse.setBirthDate(studentDto.getBirthDate());
        studentResponse.setEmail(studentDto.getEmail());
        studentResponse.setMentor(MentorConverter.toDto(studentDto.getMentor()));

        return studentResponse;
    }

}
