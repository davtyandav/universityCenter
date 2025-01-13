package com.davdavtyan.universitycenter.dto.request;

import com.davdavtyan.universitycenter.entity.LessonType;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LessonDescriptorRequest {

    private LocalDateTime data;
    private LessonType type;
    private List<LessonRequest> lessons = new ArrayList<>();

}
