package com.davdavtyan.universitycenter.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LessonResponse {

    private Long id;
    private LocalDateTime data;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private boolean isCompleted;

    private String title;
    private List<AssignmentResponse> assignments;

    private List<AttendanceResponse> attendances;

    private Long lessonDescriptorId;
    private String lessonDescriptorTitle;
}