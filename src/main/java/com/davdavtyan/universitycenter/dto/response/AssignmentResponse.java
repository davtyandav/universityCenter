package com.davdavtyan.universitycenter.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AssignmentResponse {

    private Long id;
    private String title;
    private String description;
    private LocalDateTime deadline;
    private Long fileId;
    private List<AssignmentSubmissionResponse> submissions;
}