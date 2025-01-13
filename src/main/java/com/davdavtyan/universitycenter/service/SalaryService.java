package com.davdavtyan.universitycenter.service;

import com.davdavtyan.universitycenter.LessonDescriptorRepository;
import com.davdavtyan.universitycenter.StudentRepository;
import com.davdavtyan.universitycenter.entity.Lesson;
import com.davdavtyan.universitycenter.entity.LessonType;
import com.davdavtyan.universitycenter.entity.Student;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SalaryService {

    private final StudentRepository studentRepository;
    private final LessonDescriptorRepository lessonDescriptorRepository;

    private final LessonService lessonService;

    public SalaryService(StudentRepository studentRepository, LessonDescriptorRepository lessonDescriptorRepository,
                         LessonService lessonService) {
        this.studentRepository = studentRepository;
        this.lessonDescriptorRepository = lessonDescriptorRepository;
        this.lessonService = lessonService;
    }

    public Double calculateSalary(Long mentorId, LocalDateTime start, LocalDateTime end) {
        List<Student> students = studentRepository.findStudentsByMentorId(mentorId);
        return calculate(start, end, students);
    }

    private Double calculate(LocalDateTime start, LocalDateTime end, List<Student> students) {
        List<Student> studentsByGroup = students.stream()
            .map(Student::getLessonDescriptor)
            .filter(t -> t.getType() == LessonType.GROUP)
            .flatMap(des -> des.getStudents().stream()).toList();

        List<Student> studentsNoGroup = students.stream()
            .map(Student::getLessonDescriptor)
            .filter(t -> t.getType() == LessonType.SINGLE)
            .flatMap(des -> des.getStudents().stream()).toList();

        Double noGroupSalary = calculateNoGroups(studentsNoGroup, start, end);
        Double groupSalary = calculateGroups(studentsByGroup, start, end);

        return noGroupSalary + groupSalary;

    }

    private Double calculateNoGroups(List<Student> studentsNoGroup, LocalDateTime start, LocalDateTime end) {
        double SUM_PER_LESSON = (double) ((60000 - 60000 * 5 / 100) * 50 / 100) / 12;

        return studentsNoGroup.stream()
            .mapToDouble(student -> {
                List<Lesson> lessons = completedLessonByStudent(student, start, end);
                return lessons.size() * SUM_PER_LESSON;
            })
            .sum();
    }

    private List<Lesson> completedLessonByStudent(Student student, LocalDateTime start, LocalDateTime end) {
        return lessonService.getLessonByStudentId(student.getId())
            .stream()
            .filter(lesson -> lesson.isCompleted() && dateIsBetween(lesson.getData(), start, end))
            .collect(Collectors.toList());
    }

    private boolean dateIsBetween(LocalDateTime data, LocalDateTime start, LocalDateTime end) {
        return (data.isAfter(start) && data.isBefore(end)) || data.equals(start) || data.equals(end);
    }

    private Double calculateGroups(List<Student> studentsByGroup, LocalDateTime start, LocalDateTime end) {
        double SUM_PER_LESSON = (double) ((40000 - 40000 * 5 / 100) * 40 / 100) / 12;

        return studentsByGroup.stream()
            .mapToDouble(student -> {
                List<Lesson> lessons = completedLessonByStudent(student, start, end);
                return lessons.size() * SUM_PER_LESSON;
            })
            .sum();
    }

}
