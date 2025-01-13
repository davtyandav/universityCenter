package com.davdavtyan.universitycenter.dto.response;

import com.davdavtyan.universitycenter.entity.LessonDayType;
import com.davdavtyan.universitycenter.entity.LessonType;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LessonDescriptorResponse {

    private Long id;
    private LocalDateTime data;
    private LessonType type;
    private LessonDayType dayType;
    private MentorResponse mentorResponse;
    private List<LessonResponse> lessons = new ArrayList<>();
}
