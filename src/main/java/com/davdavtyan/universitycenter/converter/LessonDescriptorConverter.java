package com.davdavtyan.universitycenter.converter;

import com.davdavtyan.universitycenter.dto.request.LessonDescriptorRequest;
import com.davdavtyan.universitycenter.dto.response.LessonDescriptorResponse;
import com.davdavtyan.universitycenter.entity.LessonDescriptor;
import java.util.stream.Collectors;

public class LessonDescriptorConverter {

    public static LessonDescriptor toEntity(LessonDescriptorRequest lessonRequest) {
        LessonDescriptor lesson = new LessonDescriptor();
        lesson.setData(lessonRequest.getData());
        lesson.setType(lessonRequest.getType());
        return lesson;
    }

    public static LessonDescriptorResponse toDto(LessonDescriptor lesson) {

        LessonDescriptorResponse studentResponse = new LessonDescriptorResponse();
        studentResponse.setId(lesson.getId());
        studentResponse.setData(lesson.getData());
        studentResponse.setType(lesson.getType());
        studentResponse.setMentorResponse(MentorConverter.toDto(lesson.getMentor()));
        studentResponse.setDayType(lesson.getDayType());
        studentResponse.setLessons(
            lesson.getLessons().stream().map(LessonConverter::toDto).collect(Collectors.toList()));
        return studentResponse;
    }

}
