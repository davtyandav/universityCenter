package com.davdavtyan.universitycenter.converter;

import com.davdavtyan.universitycenter.dto.request.LessonRequest;
import com.davdavtyan.universitycenter.dto.response.AssignmentResponse;
import com.davdavtyan.universitycenter.dto.response.AssignmentSubmissionResponse;
import com.davdavtyan.universitycenter.dto.response.AttendanceResponse;
import com.davdavtyan.universitycenter.dto.response.LessonResponse;
import com.davdavtyan.universitycenter.entity.Assignment;
import com.davdavtyan.universitycenter.entity.Lesson;
import java.util.ArrayList;

public class LessonConverter {

    public static Lesson toEntity(LessonRequest lessonRequest) {
        if (lessonRequest == null) {
            return null;
        }
        Lesson lesson = new Lesson();
        lesson.setCompleted(lessonRequest.isCompleted());
        lesson.setLessonDescriptor(lessonRequest.getLessonDescriptor());
        return lesson;
    }

    public static LessonResponse toDto(Lesson lesson) {
        if (lesson == null) {
            return null;
        }

        LessonResponse lessonResponse = new LessonResponse();
        lessonResponse.setId(lesson.getId());
        lessonResponse.setData(lesson.getData());
        lessonResponse.setStartDate(lesson.getStartDate());
        lessonResponse.setEndDate(lesson.getEndDate());
        lessonResponse.setCompleted(lesson.isCompleted());
        lessonResponse.setTitle(lesson.getTitle());

        if (lesson.getLessonDescriptor() != null) {
            lessonResponse.setLessonDescriptorId(lesson.getLessonDescriptor().getId());
            lessonResponse.setLessonDescriptorTitle(lesson.getLessonDescriptor().getTitle());
        }

        if (lesson.getAssignments() != null) {
            lessonResponse.setAssignments(lesson.getAssignments().stream()
                .map(LessonConverter::toAssignmentDto)
                .toList());
        } else {
            lessonResponse.setAssignments(new ArrayList<>());
        }

        if (lesson.getAttendances() != null) {
            lessonResponse.setAttendances(lesson.getAttendances().stream()
                .map(attendance -> {
                    AttendanceResponse attr = new AttendanceResponse();
                    attr.setId(attendance.getId());
                    attr.setPresent(attendance.isPresent());
                    attr.setNote(attendance.getNote());
                    if (attendance.getStudent() != null) {
                        attr.setStudentId(attendance.getStudent().getId());
                        if (attendance.getStudent().getUser() != null) {
                            attr.setStudentName(attendance.getStudent().getUser().getName());
                            attr.setStudentLastName(attendance.getStudent().getUser().getLastName());
                        }
                    }
                    return attr;
                })
                .toList());
        } else {
            lessonResponse.setAttendances(new ArrayList<>());
        }

        return lessonResponse;
    }

    private static AssignmentResponse toAssignmentDto(Assignment assignment) {
        if (assignment == null) {
            return null;
        }
        AssignmentResponse response = new AssignmentResponse();
        response.setId(assignment.getId());
        response.setTitle(assignment.getTitle());
        response.setDescription(assignment.getDescription());
        response.setDeadline(assignment.getDeadline());

        if (assignment.getFile() != null) {
            response.setFileId(assignment.getFile().getId());
        }

        // === МАППИНГ ОТВЕТОВ СТУДЕНТОВ ===
        if (assignment.getSubmissions() != null) {
            response.setSubmissions(assignment.getSubmissions().stream()
                .map(sub -> {
                    AssignmentSubmissionResponse subDto = new AssignmentSubmissionResponse();
                    subDto.setId(sub.getId());
                    subDto.setStudentComment(sub.getStudentComment());
                    subDto.setSubmittedAt(sub.getSubmittedAt());
                    subDto.setGrade(sub.getGrade());
                    subDto.setMentorFeedback(sub.getMentorFeedback());
                    subDto.setAssignmentId(assignment.getId());
                    if (sub.getStudent() != null) {
                        subDto.setStudentId(sub.getStudent().getId());
                        if (sub.getStudent().getUser() != null) {
                            subDto.setStudentName(sub.getStudent().getUser().getName());
                            subDto.setStudentLastName(sub.getStudent().getUser().getLastName());
                        }
                    }
                    if (sub.getStudentFile() != null) {
                        subDto.setStudentFileId(sub.getStudentFile().getId());
                    }
                    return subDto;
                })
                .toList());
        } else {
            response.setSubmissions(new ArrayList<>());
        }

        return response;
    }
}