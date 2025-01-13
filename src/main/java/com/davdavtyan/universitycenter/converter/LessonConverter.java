package com.davdavtyan.universitycenter.converter;

import com.davdavtyan.universitycenter.dto.request.LessonRequest;
import com.davdavtyan.universitycenter.dto.response.LessonResponse;
import com.davdavtyan.universitycenter.entity.Lesson;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class LessonConverter {

    public static Lesson toEntity(LessonRequest lessonRequest) {
        Lesson lesson = new Lesson();
        lesson.setData(lessonRequest.getData());
        lesson.setCompleted(lessonRequest.isCompleted());
        lesson.setType(lessonRequest.getType());
        lesson.setStudents(new ArrayList<>());
        return lesson;
    }

    public static LessonResponse toDto(Lesson lesson) {
        LessonResponse studentResponse = new LessonResponse();
        studentResponse.setData(lesson.getData());
        studentResponse.setCompleted(lesson.isCompleted());
        studentResponse.setType(lesson.getType());
//        studentResponse.setStudents(
//            lesson.getStudents().stream().map(StudentConverter::toDto).collect(Collectors.toList()));

        return studentResponse;
    }

}
