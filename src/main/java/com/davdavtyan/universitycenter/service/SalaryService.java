package com.davdavtyan.universitycenter.service;

import com.davdavtyan.universitycenter.LessonDescriptorRepository;
import com.davdavtyan.universitycenter.dto.response.SalaryResponse;
import com.davdavtyan.universitycenter.entity.LessonDescriptor;
import com.davdavtyan.universitycenter.entity.LessonType;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class SalaryService {

    private final LessonDescriptorRepository lessonDescriptorRepository;

    public SalaryService(LessonDescriptorRepository lessonDescriptorRepository) {
        this.lessonDescriptorRepository = lessonDescriptorRepository;
    }

    public SalaryResponse calculateSalary(Long mentorId, LocalDateTime start, LocalDateTime end) {
        List<LessonDescriptor> lessonDescriptors =
            lessonDescriptorRepository.findLessonDescriptorsByMentor_Id(mentorId);
        return calculate(start, end, lessonDescriptors);
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
                double currentGroupSalary = completedLessonsCount * baseGroupSumPerLesson * studentsCount;
                groupSalary += currentGroupSalary;

            } else if (descriptor.getType() == LessonType.SINGLE) {
                double singleSumPerLesson = (double) ((60000 - 60000 * 5 / 100) * 50 / 100) / 12;
                double currentSingleSalary = completedLessonsCount * singleSumPerLesson;
                singleSalary += currentSingleSalary;

            }
        }

        double totalSalary = groupSalary + singleSalary;

        return new SalaryResponse(groupSalary, singleSalary, totalSalary);
    }

    private boolean dateIsBetween(LocalDateTime data, LocalDateTime start, LocalDateTime end) {
        return (data.isAfter(start) && data.isBefore(end)) || data.equals(start) || data.equals(end);
    }

}
