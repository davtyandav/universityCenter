package com.davdavtyan.universitycenter.service;

import com.davdavtyan.universitycenter.LessonDescriptorRepository;
import com.davdavtyan.universitycenter.SalaryReportRepository;
import com.davdavtyan.universitycenter.dto.response.SalaryResponse;
import com.davdavtyan.universitycenter.entity.LessonDescriptor;
import com.davdavtyan.universitycenter.entity.LessonType;
import com.davdavtyan.universitycenter.entity.Mentor;
import com.davdavtyan.universitycenter.entity.SalaryReportEntity;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SalaryService {

    private final LessonDescriptorRepository lessonDescriptorRepository;
    private final SalaryReportRepository salaryReportRepository;
    private final jakarta.persistence.EntityManager entityManager;

    public SalaryService(LessonDescriptorRepository lessonDescriptorRepository,
                         SalaryReportRepository salaryReportRepository,
                         jakarta.persistence.EntityManager entityManager) {
        this.lessonDescriptorRepository = lessonDescriptorRepository;
        this.salaryReportRepository = salaryReportRepository;
        this.entityManager = entityManager;
    }

    public SalaryResponse calculateSalary(Long mentorId, LocalDateTime start, LocalDateTime end) {
        List<LessonDescriptor> lessonDescriptors =
            lessonDescriptorRepository.findLessonDescriptorsByMentor_Id(mentorId);
        return calculate(start, end, lessonDescriptors);
    }

    @Transactional
    public SalaryReportEntity generateAndSaveReport(Long mentorId, LocalDateTime start, LocalDateTime end) {
        Mentor mentor = entityManager.find(Mentor.class, mentorId);
        if (mentor == null) {
            throw new IllegalArgumentException("Mentor not found");
        }

        SalaryResponse salary = calculateSalary(mentorId, start, end);
        byte[] pdfBytes = generatePdfBytes(mentor, salary, start, end);

        SalaryReportEntity report = new SalaryReportEntity();
        report.setMentor(mentor);
        report.setStartDate(start);
        report.setEndDate(end);
        report.setGeneratedAt(LocalDateTime.now());
        report.setGroupSalary(salary.getGroupSalary());
        report.setSingleSalary(salary.getSingleSalary());
        report.setTotalSalary(salary.getTotalSalary());
        report.setPdfData(pdfBytes);

        return salaryReportRepository.save(report);
    }

    public List<SalaryReportEntity> getMentorReports(Long mentorId) {
        return salaryReportRepository.findAllByMentor_IdOrderByGeneratedAtDesc(mentorId);
    }

    @Transactional
    public void deleteReport(Long reportId) {
        salaryReportRepository.deleteById(reportId);
    }

    private SalaryResponse calculate(LocalDateTime start, LocalDateTime end, List<LessonDescriptor> lessonDescriptors) {
        double groupSalary = 0.0;
        double singleSalary = 0.0;

        for (LessonDescriptor descriptor : lessonDescriptors) {
            long completedLessonsCount = descriptor.getLessons().stream()
                .filter(lesson -> lesson.isCompleted() && dateIsBetween(lesson.getData(), start, end))
                .count();

            if (completedLessonsCount == 0) {
                continue;
            }

            if (descriptor.getType() == LessonType.GROUP) {
                int studentsCount = descriptor.getStudents() != null ? descriptor.getStudents().size() : 0;
                double baseGroupSumPerLesson = (double) ((40000 - 40000 * 5 / 100) * 40 / 100) / 12;
                groupSalary += completedLessonsCount * baseGroupSumPerLesson * studentsCount;
            } else if (descriptor.getType() == LessonType.SINGLE) {
                double singleSumPerLesson = (double) ((60000 - 60000 * 5 / 100) * 50 / 100) / 12;
                singleSalary += completedLessonsCount * singleSumPerLesson;
            }
        }
        return new SalaryResponse(groupSalary, singleSalary, groupSalary + singleSalary);
    }

    private boolean dateIsBetween(LocalDateTime data, LocalDateTime start, LocalDateTime end) {
        return (data.isAfter(start) && data.isBefore(end)) || data.equals(start) || data.equals(end);
    }

    private byte[] generatePdfBytes(Mentor mentor, SalaryResponse salary, LocalDateTime start, LocalDateTime end) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Document document = new Document(PageSize.A4, 36, 36, 54, 36);

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Paragraph title = new Paragraph("Salary Financial Report", titleFont);
            title.setAlignment(Element.ALIGN_LEFT);
            title.setSpacingAfter(15);
            document.add(title);

            Font infoFont = FontFactory.getFont(FontFactory.HELVETICA, 11);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            document.add(new Paragraph("Mentor: " + mentor.getUser().getName() + " " + mentor.getUser().getLastName(),
                infoFont));
            document.add(
                new Paragraph("Period: " + start.format(formatter) + " to " + end.format(formatter), infoFont));
            document.add(new Paragraph("Generated on: " + LocalDateTime.now().format(formatter), infoFont));
            document.add(new Paragraph(" ", infoFont));

            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10);

            PdfPCell cell1 = new PdfPCell(new Phrase("Salary Category"));
            cell1.setBackgroundColor(new java.awt.Color(5, 150, 105));
            PdfPCell cell2 = new PdfPCell(new Phrase("Amount (AMD)"));
            cell2.setBackgroundColor(new java.awt.Color(5, 150, 105));

            table.addCell(cell1);
            table.addCell(cell2);

            table.addCell("Group Lessons Salary");
            table.addCell(String.format("%.0f Dram", salary.getGroupSalary()));

            table.addCell("Single Lessons Salary");
            table.addCell(String.format("%.0f Dram", salary.getSingleSalary()));

            table.addCell("Total Payout");
            table.addCell(String.format("%.0f Dram", salary.getTotalSalary()));

            document.add(table);
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return out.toByteArray();
    }

}