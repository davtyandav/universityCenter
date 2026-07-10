package com.davdavtyan.universitycenter.dto.response;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AssignmentSubmissionResponse {
    private Long id;
    private Long assignmentId;
    private String assignmentTitle;
    private Long studentId;
    private String studentName;
    private String studentLastName;
    private String studentComment;
    private Long studentFileId;
    private LocalDateTime submittedAt;
    private String grade;
    private String mentorFeedback;
}