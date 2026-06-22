package com.davdavtyan.universitycenter.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.*;

@Entity
@Table(name = "salary_reports")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SalaryReportEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private LocalDateTime generatedAt;
    private Double groupSalary;
    private Double singleSalary;
    private Double totalSalary;

    @Column(name = "pdf_data", nullable = false)
    private byte[] pdfData;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mentor_id", nullable = false)
    private Mentor mentor;

}