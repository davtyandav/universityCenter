package com.davdavtyan.universitycenter.controller;

import com.davdavtyan.universitycenter.dto.request.CalculateRequest;
import com.davdavtyan.universitycenter.service.SalaryService;
import java.time.LocalDateTime;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/salaries")
public class SalaryController {

    private final SalaryService salaryService;
    public SalaryController(SalaryService salaryService) {
        this.salaryService = salaryService;
    }

    @PostMapping("/calculate")
    public Double calculate(@RequestBody CalculateRequest calculate) {
        Long mentorId = calculate.getMentorId();
        LocalDateTime start = calculate.getStart();
        LocalDateTime end = calculate.getEnd();
        return salaryService.calculateSalary(mentorId, start, end);
    }

}
