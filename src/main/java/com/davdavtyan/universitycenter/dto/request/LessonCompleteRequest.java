package com.davdavtyan.universitycenter.dto.request;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class LessonCompleteRequest {
    private String title; // Тема проведенного урока
    private List<Long> presentStudentIds; // ID студентов, которые БЫЛИ на уроке
    private List<AssignmentRequest> assignments; // Список добавляемых задач (может быть пустым)
}