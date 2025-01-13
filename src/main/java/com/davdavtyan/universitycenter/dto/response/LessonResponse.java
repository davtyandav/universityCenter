package com.davdavtyan.universitycenter.dto.response;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LessonResponse {

    private Long id;
    private LocalDateTime data;
    private boolean isCompleted;
}
