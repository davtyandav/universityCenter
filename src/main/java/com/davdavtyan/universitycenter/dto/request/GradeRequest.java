package com.davdavtyan.universitycenter.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GradeRequest {
    private String grade;
    private String mentorFeedback;
}