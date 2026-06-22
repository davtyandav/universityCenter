package com.davdavtyan.universitycenter.controller;

import com.davdavtyan.universitycenter.SalaryReportRepository;
import com.davdavtyan.universitycenter.dto.response.SalaryReportResponse;
import com.davdavtyan.universitycenter.dto.response.SalaryResponse;
import com.davdavtyan.universitycenter.entity.SalaryReportEntity;
import com.davdavtyan.universitycenter.service.SalaryService;
import java.time.LocalDate;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/salaries")
@CrossOrigin(origins = "*")
public class SalaryController {

    private final SalaryService salaryService;
    private final SalaryReportRepository salaryReportRepository; // Добавляем репозиторий

    public SalaryController(SalaryService salaryService, SalaryReportRepository salaryReportRepository) {
        this.salaryService = salaryService;
        this.salaryReportRepository = salaryReportRepository;
    }

    @GetMapping("/calculate")
    public ResponseEntity<SalaryResponse> calculate(@RequestParam Long mentorId,
                                                    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                                    LocalDate start,
                                                    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                                    LocalDate end) {
        return ResponseEntity.ok(salaryService.calculateSalary(mentorId, start.atStartOfDay(), end.atStartOfDay()));
    }

    @PostMapping("/report")
    public ResponseEntity<SalaryReportEntity> generateReport(@RequestParam Long mentorId,
                                                             @RequestParam
                                                             @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                                             LocalDate start,
                                                             @RequestParam
                                                             @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                                             LocalDate end) {
        return ResponseEntity.ok(
            salaryService.generateAndSaveReport(mentorId, start.atStartOfDay(), end.atStartOfDay()));
    }

    @GetMapping("/mentor/{mentorId}/reports")
    public ResponseEntity<List<SalaryReportResponse>> getReports(@PathVariable Long mentorId) {
        List<SalaryReportResponse> responses = salaryService.getMentorReports(mentorId).stream()
            .map(report -> new SalaryReportResponse(
                report.getId(),
                report.getStartDate(),
                report.getEndDate(),
                report.getGeneratedAt(),
                report.getGroupSalary(),
                report.getSingleSalary(),
                report.getTotalSalary()
            ))
            .toList();

        return ResponseEntity.ok(responses);
    }

    @GetMapping("/reports/{id}/download")
    public ResponseEntity<byte[]> downloadReport(@PathVariable Long id) {
        SalaryReportEntity report = salaryReportRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Report not found with ID: " + id));

        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"Salary_Report_" + id + ".pdf\"")
            .contentType(MediaType.APPLICATION_PDF)
            .body(report.getPdfData());
    }

    @DeleteMapping("/reports/{id}")
    public ResponseEntity<Void> deleteReport(@PathVariable Long id) {
        salaryService.deleteReport(id);
        return ResponseEntity.noContent().build();
    }

}