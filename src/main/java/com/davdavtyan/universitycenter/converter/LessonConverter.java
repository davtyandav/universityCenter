package com.davdavtyan.universitycenter.converter;

import com.davdavtyan.universitycenter.dto.request.LessonRequest;
import com.davdavtyan.universitycenter.dto.response.LessonResponse;
import com.davdavtyan.universitycenter.entity.Lesson;

public class LessonConverter {

    public static Lesson toEntity(LessonRequest lessonRequest) {
        Lesson lesson = new Lesson();
        lesson.setData(lessonRequest.getData());
        lesson.setCompleted(lessonRequest.isCompleted());
        lesson.setLessonDescriptor(lessonRequest.getLessonDescriptor());
        return lesson;
    }

    public static LessonResponse toDto(Lesson lesson) {

        LessonResponse studentResponse = new LessonResponse();
        studentResponse.setId(lesson.getId());
        studentResponse.setData(lesson.getData());
        studentResponse.setCompleted(lesson.isCompleted());

        return studentResponse;
    }

}
