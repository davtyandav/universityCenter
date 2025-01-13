package com.davdavtyan.universitycenter.dto.response;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LessonStudentsResponse {
    private LessonResponse lesson; // Данные об уроке
    private List<StudentLessonResponse> students; // Список студентов
}