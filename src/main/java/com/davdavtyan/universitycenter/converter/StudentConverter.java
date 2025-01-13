package com.davdavtyan.universitycenter.converter;

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
        student.setLessonDescriptor(studentRequest.getLessonDescriptor());
        return student;
    }

    public static StudentResponse toDto(Student student) {
        StudentResponse studentResponse = new StudentResponse();
        studentResponse.setId(student.getId());
        studentResponse.setName(student.getName());
        studentResponse.setLastName(student.getLastName());
        studentResponse.setBirthDate(student.getBirthDate());
        studentResponse.setEmail(student.getEmail());

        studentResponse.setMentor(
            student.getMentor() == null ? null : MentorConverter.toDto(student.getMentor()));

        studentResponse.setLessonDescriptor(
            student.getLessonDescriptor() != null ? LessonDescriptorConverter.toDto(student.getLessonDescriptor()) :
                null);

        return studentResponse;
    }

}
