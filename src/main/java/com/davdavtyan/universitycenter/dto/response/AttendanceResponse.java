package com.davdavtyan.universitycenter.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AttendanceResponse {
    private Long id;
    private Long studentId;
    private String studentName;
    private String studentLastName;
    private boolean isPresent;
    private String note;
}