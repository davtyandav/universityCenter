package com.davdavtyan.universitycenter.converter;

import com.davdavtyan.universitycenter.dto.response.AssignmentSubmissionResponse;
import com.davdavtyan.universitycenter.entity.AssignmentSubmission;

public class AssignmentSubmissionConverter {

    public static AssignmentSubmissionResponse toDto(AssignmentSubmission submission) {
        if (submission == null) {
            return null;
        }

        AssignmentSubmissionResponse dto = new AssignmentSubmissionResponse();
        dto.setId(submission.getId());
        dto.setStudentComment(submission.getStudentComment());
        dto.setSubmittedAt(submission.getSubmittedAt());
        dto.setGrade(submission.getGrade());
        dto.setMentorFeedback(submission.getMentorFeedback());

        // Маппинг задачи
        if (submission.getAssignment() != null) {
            dto.setAssignmentId(submission.getAssignment().getId());
            dto.setAssignmentTitle(submission.getAssignment().getTitle());
        }

        // Маппинг студента
        if (submission.getStudent() != null) {
            dto.setStudentId(submission.getStudent().getId());
            if (submission.getStudent().getUser() != null) {
                dto.setStudentName(submission.getStudent().getUser().getName());
                dto.setStudentLastName(submission.getStudent().getUser().getLastName());
            }
        }

        // Маппинг файла решения
        if (submission.getStudentFile() != null) {
            dto.setStudentFileId(submission.getStudentFile().getId());
        }

        return dto;
    }
}