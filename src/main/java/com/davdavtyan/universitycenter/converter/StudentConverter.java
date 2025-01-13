package com.davdavtyan.universitycenter.converter;

import com.davdavtyan.universitycenter.dto.request.StudentRequest;
import com.davdavtyan.universitycenter.dto.response.LessonDescriptorResponse;
import com.davdavtyan.universitycenter.dto.response.MentorResponse;
import com.davdavtyan.universitycenter.dto.response.StudentLessonResponse;
import com.davdavtyan.universitycenter.dto.response.StudentResponse;
import com.davdavtyan.universitycenter.entity.Student;
import com.davdavtyan.universitycenter.entity.User;
import org.springframework.stereotype.Component;

@Component
public class StudentConverter {

    public static Student toEntity(StudentRequest studentRequest) {
        Student student = new Student();
        student.setId(studentRequest.getId());
        student.setBirthDate(studentRequest.getBirthDate());
        student.setLessonDescriptor(studentRequest.getLessonDescriptor());
        return student;
    }

    public static StudentResponse toDto(Student student) {
        User user = student.getUser();

        StudentResponse studentResponse = new StudentResponse();
        studentResponse.setId(student.getId());
        studentResponse.setUser(UserConverter.toDto(user));
        studentResponse.setBirthDate(student.getBirthDate());
        studentResponse.setMentor(getMentorResponse(student));
        studentResponse.setLessonDescriptor(getLessonDescriptorResponse(student));

        return studentResponse;
    }

    public static StudentLessonResponse toStudentLessonRDto(Student student) {
        User user = student.getUser();

        StudentLessonResponse studentResponse = new StudentLessonResponse();
        studentResponse.setId(student.getId());
        studentResponse.setName(user.getName());
        studentResponse.setLastName(user.getLastName());
        studentResponse.setBirthDate(student.getBirthDate());
        studentResponse.setEmail(user.getEmail());
        return studentResponse;
    }

    private static MentorResponse getMentorResponse(Student student) {
        return student.getMentor() == null
            ? null
            : MentorConverter.toDto(student.getMentor());
    }

    private static LessonDescriptorResponse getLessonDescriptorResponse(Student student) {
        return student.getLessonDescriptor() == null
            ? null
            : LessonDescriptorConverter.toDto(student.getLessonDescriptor());
    }

}
