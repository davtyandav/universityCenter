package com.davdavtyan.universitycenter.dto.response;

import com.davdavtyan.universitycenter.entity.LessonType;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LessonResponse {

    private Long id;
    private LocalDateTime data;
    private boolean isCompleted;
    private LessonType type;
    private List<StudentResponse> students;

}
