package com.davdavtyan.universitycenter.dto.request;

import com.davdavtyan.universitycenter.entity.LessonDayType;
import com.davdavtyan.universitycenter.entity.LessonType;
import com.davdavtyan.universitycenter.entity.MonthType;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LessonDescriptorRequest {

    private LocalDateTime startDate;
    private LessonType type;
    private LessonDayType lessonDayType;
    private MonthType monthType;
    private String title;
    private Long mentorId;

}
