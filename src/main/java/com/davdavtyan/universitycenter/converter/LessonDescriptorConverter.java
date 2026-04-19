package com.davdavtyan.universitycenter.converter;

import com.davdavtyan.universitycenter.dto.request.LessonDescriptorRequest;
import com.davdavtyan.universitycenter.dto.response.LessonDescriptorResponse;
import com.davdavtyan.universitycenter.dto.response.LessonInfo;
import com.davdavtyan.universitycenter.dto.response.MentorResponse;
import com.davdavtyan.universitycenter.dto.response.StudentResponse;
import com.davdavtyan.universitycenter.entity.LessonDescriptor;
import com.davdavtyan.universitycenter.entity.MonthType;
import com.davdavtyan.universitycenter.entity.Student;
import com.davdavtyan.universitycenter.entity.User;
import java.util.List;
import java.util.stream.Collectors;

public class LessonDescriptorConverter {

    public static LessonDescriptor toEntity(LessonDescriptorRequest request) {
        LessonDescriptor lessonDescriptor = new LessonDescriptor();
        lessonDescriptor.setStartDate(request.getStartDate());
        lessonDescriptor.setType(request.getType());
        lessonDescriptor.setTitle(request.getTitle());
        lessonDescriptor.setDayType(request.getLessonDayType());
        return lessonDescriptor;
    }

    public static LessonDescriptorResponse toDto(LessonDescriptor lessonDescriptor) {

        LessonDescriptorResponse descriptorResponse = new LessonDescriptorResponse();
        descriptorResponse.setId(lessonDescriptor.getId());
        descriptorResponse.setData(lessonDescriptor.getStartDate());
        descriptorResponse.setType(lessonDescriptor.getType());
        descriptorResponse.setTitle(lessonDescriptor.getTitle());
        descriptorResponse.setMentorResponse(MentorConverter.toDto(lessonDescriptor.getMentor()));
        descriptorResponse.setDayType(lessonDescriptor.getDayType());
        descriptorResponse.setLessonInfo(lessonsInfo(lessonDescriptor));
        descriptorResponse.setStudentResponses(studentResponses(lessonDescriptor));
        return descriptorResponse;
    }

    private static List<LessonInfo> lessonsInfo(LessonDescriptor lessonDescriptor) {
        return lessonDescriptor.getLessons().stream()
            .map(LessonConverter::toDto)
            .collect(Collectors.groupingBy(
                lesson -> lesson.getData().getMonth().name()
            ))
            .entrySet().stream()
            .map(entry -> {
                LessonInfo info = new LessonInfo();
                info.setMonthType(MonthType.valueOf(entry.getKey()));
                info.setLessons(entry.getValue());
                return info;
            })
            .collect(Collectors.toList());
    }

    private static List<StudentResponse> studentResponses(LessonDescriptor lessonDescriptor) {
        return lessonDescriptor.getStudents().stream()
            .map(LessonDescriptorConverter::toDto)
            .collect(Collectors.toList());
    }

    public static StudentResponse toDto(Student student) {
        User user = student.getUser();

        StudentResponse studentResponse = new StudentResponse();
        studentResponse.setId(student.getId());
        studentResponse.setUser(UserConverter.toDto(user));
        studentResponse.setBirthDate(student.getBirthDate());
        studentResponse.setMentor(getMentorResponse(student));
        return studentResponse;
    }

    private static MentorResponse getMentorResponse(Student student) {
        return student.getMentor() == null
            ? null
            : MentorConverter.toDto(student.getMentor());
    }

}
