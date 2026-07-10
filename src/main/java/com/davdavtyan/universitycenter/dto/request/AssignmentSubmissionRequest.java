package com.davdavtyan.universitycenter.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AssignmentSubmissionRequest {
    private Long assignmentId;
    private Long studentId;
    private String studentComment;
    private Long fileId; // ID загруженного файла из FileController
}