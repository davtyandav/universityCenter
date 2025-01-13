package com.davdavtyan.universitycenter.dto.response;

import com.davdavtyan.universitycenter.entity.MonthType;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LessonInfo {

    private MonthType monthType;
    private List<LessonResponse> lessons = new ArrayList<>();

}
