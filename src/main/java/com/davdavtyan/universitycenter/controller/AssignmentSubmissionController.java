package com.davdavtyan.universitycenter.controller;

import com.davdavtyan.universitycenter.converter.AssignmentSubmissionConverter;
import com.davdavtyan.universitycenter.dto.request.AssignmentSubmissionRequest;
import com.davdavtyan.universitycenter.dto.request.GradeRequest;
import com.davdavtyan.universitycenter.dto.response.AssignmentSubmissionResponse;
import com.davdavtyan.universitycenter.entity.AssignmentSubmission;
import com.davdavtyan.universitycenter.service.AssignmentSubmissionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/submissions")
public class AssignmentSubmissionController {

    private final AssignmentSubmissionService submissionService;

    public AssignmentSubmissionController(AssignmentSubmissionService submissionService) {
        this.submissionService = submissionService;
    }

    // Студент: отправить домашку на проверку
    @PostMapping
    public ResponseEntity<AssignmentSubmissionResponse> submit(@RequestBody AssignmentSubmissionRequest request) {
        AssignmentSubmission submission = submissionService.submitAssignment(request);
        return ResponseEntity.ok(AssignmentSubmissionConverter.toDto(submission));
    }

    // Ментор: оценить домашку студента
    @PutMapping("/{id}/grade")
    public ResponseEntity<AssignmentSubmissionResponse> grade(@PathVariable Long id, @RequestBody GradeRequest request) {
        AssignmentSubmission submission = submissionService.gradeSubmission(id, request);
        return ResponseEntity.ok(AssignmentSubmissionConverter.toDto(submission));
    }

    // Ментор: посмотреть кто что сдал по конкретной задаче
    @GetMapping("/assignment/{assignmentId}")
    public ResponseEntity<List<AssignmentSubmissionResponse>> getByAssignment(@PathVariable Long assignmentId) {
        List<AssignmentSubmissionResponse> responses = submissionService.getSubmissionsByAssignment(assignmentId)
            .stream()
            .map(AssignmentSubmissionConverter::toDto)
            .toList();
        return ResponseEntity.ok(responses);
    }

    // Студент: посмотреть свои сданные работы и оценки
    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<AssignmentSubmissionResponse>> getByStudent(@PathVariable Long studentId) {
        List<AssignmentSubmissionResponse> responses = submissionService.getSubmissionsByStudent(studentId)
            .stream()
            .map(AssignmentSubmissionConverter::toDto)
            .toList();
        return ResponseEntity.ok(responses);
    }
}