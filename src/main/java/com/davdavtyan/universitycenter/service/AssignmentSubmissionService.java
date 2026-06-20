package com.davdavtyan.universitycenter.service;

import com.davdavtyan.universitycenter.FileRepository;
import com.davdavtyan.universitycenter.StudentRepository;
import com.davdavtyan.universitycenter.dto.request.AssignmentSubmissionRequest;
import com.davdavtyan.universitycenter.dto.request.GradeRequest;
import com.davdavtyan.universitycenter.entity.Assignment;
import com.davdavtyan.universitycenter.entity.AssignmentSubmission;
import com.davdavtyan.universitycenter.entity.Student;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AssignmentSubmissionService {

    private final AssignmentSubmissionRepository submissionRepository;
    private final AssignmentRepository assignmentRepository;
    private final StudentRepository studentRepository;
    private final FileRepository fileRepository;

    public AssignmentSubmissionService(AssignmentSubmissionRepository submissionRepository,
                                       AssignmentRepository assignmentRepository,
                                       StudentRepository studentRepository,
                                       FileRepository fileRepository) {
        this.submissionRepository = submissionRepository;
        this.assignmentRepository = assignmentRepository;
        this.studentRepository = studentRepository;
        this.fileRepository = fileRepository;
    }

    // 1. Студент отправляет решение задачи
    @Transactional
    public AssignmentSubmission submitAssignment(AssignmentSubmissionRequest request) {
        Assignment assignment = assignmentRepository.findById(request.getAssignmentId())
            .orElseThrow(() -> new EntityNotFoundException("Assignment not found"));

        Student student = studentRepository.findById(request.getStudentId())
            .orElseThrow(() -> new EntityNotFoundException("Student not found"));

        AssignmentSubmission submission = new AssignmentSubmission();
        submission.setAssignment(assignment);
        submission.setStudent(student);
        submission.setStudentComment(request.getStudentComment());
        submission.setSubmittedAt(LocalDateTime.now());

        if (request.getFileId() != null) {
            fileRepository.findById(request.getFileId()).ifPresent(submission::setStudentFile);
        }

        return submissionRepository.save(submission);
    }

    // 2. Ментор ставит оценку и пишет фидбек
    @Transactional
    public AssignmentSubmission gradeSubmission(Long submissionId, GradeRequest request) {
        AssignmentSubmission submission = submissionRepository.findById(submissionId)
            .orElseThrow(() -> new EntityNotFoundException("Submission not found"));

        submission.setGrade(request.getGrade());
        submission.setMentorFeedback(request.getMentorFeedback());

        return submissionRepository.save(submission);
    }

    // 3. Получить все сданные работы по конкретной задаче (для ментора)
    public List<AssignmentSubmission> getSubmissionsByAssignment(Long assignmentId) {
        return submissionRepository.findByAssignmentId(assignmentId);
    }

    // 4. Получить историю сдач конкретного студента (для личного кабинета студента)
    public List<AssignmentSubmission> getSubmissionsByStudent(Long studentId) {
        return submissionRepository.findByStudentId(studentId);
    }

}