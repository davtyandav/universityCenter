package com.davdavtyan.universitycenter.service;

import com.davdavtyan.universitycenter.LessonDescriptorRepository;
import com.davdavtyan.universitycenter.entity.Lesson;
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

    public Double calculateSalary(Long mentorId, LocalDateTime start, LocalDateTime end) {
        List<LessonDescriptor> lessonDescriptors =
            lessonDescriptorRepository.findLessonDescriptorsByMentor_Id(mentorId);
        return calculate(start, end, lessonDescriptors);
    }

    private Double calculate(LocalDateTime start, LocalDateTime end, List<LessonDescriptor> lessonDescriptors) {
        List<Lesson> lessonsByGroup = lessonDescriptors.stream()
            .filter(lessonDescriptor -> lessonDescriptor.getType() == LessonType.GROUP)
            .flatMap(des -> des.getLessons().stream()).toList();

        List<Lesson> lessonsNoGroup = lessonDescriptors.stream()
            .filter(lessonDescriptor -> lessonDescriptor.getType() == LessonType.SINGLE)
            .flatMap(des -> des.getLessons().stream()).toList();

        Double noGroupSalary = calculateNoGroups(lessonsNoGroup, start, end);
        Double groupSalary = calculateGroups(lessonsByGroup, start, end);

        return noGroupSalary + groupSalary;

    }

    private Double calculateNoGroups(List<Lesson> lessons, LocalDateTime start, LocalDateTime end) {
        double SUM_PER_LESSON = (double) ((60000 - 60000 * 5 / 100) * 50 / 100) / 12;

        long count = lessons.stream()
            .filter(lesson -> lesson.isCompleted() && dateIsBetween(lesson.getData(), start, end)).count();
        return count * SUM_PER_LESSON;

    }

    private boolean dateIsBetween(LocalDateTime data, LocalDateTime start, LocalDateTime end) {
        return (data.isAfter(start) && data.isBefore(end)) || data.equals(start) || data.equals(end);
    }

    private Double calculateGroups(List<Lesson> lessons, LocalDateTime start, LocalDateTime end) {
        double SUM_PER_LESSON = (double) ((40000 - 40000 * 5 / 100) * 40 / 100) / 12;
        long count = lessons.stream()
            .filter(lesson -> lesson.isCompleted() && dateIsBetween(lesson.getData(), start, end)).count();
        return count * SUM_PER_LESSON;
    }

}
