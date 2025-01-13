package com.davdavtyan.universitycenter.dto.request;

import com.davdavtyan.universitycenter.entity.MonthType;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LessonGenerateRequest {

    private Long lessonDescriptorId;
    private MonthType monthType;

}
