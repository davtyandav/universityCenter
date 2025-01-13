package com.davdavtyan.universitycenter.converter;

import com.davdavtyan.universitycenter.dto.request.StudentRequest;
import com.davdavtyan.universitycenter.dto.response.StudentResponse;
import com.davdavtyan.universitycenter.entity.Lesson;
import com.davdavtyan.universitycenter.entity.Student;
import java.util.ArrayList;
import java.util.List;

public class StudentConverter {

    public static Student toEntity(StudentRequest studentRequest) {
        Student student = new Student();
        student.setId(studentRequest.getId());
        student.setName(studentRequest.getName());
        student.setLastName(studentRequest.getLastName());
        student.setBirthDate(studentRequest.getBirthDate());
        student.setEmail(studentRequest.getEmail());
        student.setLessons(new ArrayList<>());
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
        List<Lesson> lessons = student.getLessons();
        studentResponse.setLessonType(lessons == null || lessons.isEmpty() ? null :
            LessonConverter.toDto(lessons.get(0)).getType().name());

        return studentResponse;
    }

}
