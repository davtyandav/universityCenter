package com.davdavtyan.universitycenter.dto.request;

import com.davdavtyan.universitycenter.entity.LessonType;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CalculateRequest {

    private Long mentorId;
    private LocalDateTime start;
    private LocalDateTime end;
    private LessonType type;

}
