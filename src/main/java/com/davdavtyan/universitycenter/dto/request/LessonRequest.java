package com.davdavtyan.universitycenter.dto.request;

import com.davdavtyan.universitycenter.entity.LessonDescriptor;
import com.davdavtyan.universitycenter.entity.LessonType;
import com.davdavtyan.universitycenter.entity.Student;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LessonRequest {

    private LocalDateTime data;
    private boolean isCompleted;
    private LessonType type;
    private Student student;
    private LessonDescriptor lessonDescriptor;

}
