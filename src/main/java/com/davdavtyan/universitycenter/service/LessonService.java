package com.davdavtyan.universitycenter.service;

import com.davdavtyan.universitycenter.LessonDescriptorRepository;
import com.davdavtyan.universitycenter.LessonRepository;
import com.davdavtyan.universitycenter.StudentRepository;
import com.davdavtyan.universitycenter.entity.Lesson;
import com.davdavtyan.universitycenter.entity.LessonDayType;
import com.davdavtyan.universitycenter.entity.LessonDescriptor;
import com.davdavtyan.universitycenter.entity.MonthType;
import jakarta.transaction.Transactional;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.YearMonth;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LessonService {

    private final LessonRepository lessonRepository;
    private final LessonDescriptorRepository lessonDescriptorRepository;
    private final StudentRepository studentRepository;

    @Autowired
    public LessonService(LessonRepository lessonRepository, LessonDescriptorRepository lessonDescriptorRepository,
                         StudentRepository studentRepository) {
        this.lessonRepository = lessonRepository;
        this.lessonDescriptorRepository = lessonDescriptorRepository;
        this.studentRepository = studentRepository;
    }

    public Lesson addLesson(Lesson lesson) {
        return lessonRepository.save(lesson);
    }

    public List<Lesson> getAllLessons() {
        return lessonRepository.findAll();
    }

    public Optional<Lesson> getLessonById(Long id) {
        return lessonRepository.findById(id);
    }

    public void deleteLessonById(Long id) {
        lessonRepository.deleteById(id);
    }

    public Lesson updateLesson(Long id, Lesson lesson) {
        return getLessonById(id)
            .map(existingLesson -> {
                existingLesson.setData(lesson.getData());
                existingLesson.setCompleted(lesson.isCompleted());
                return lessonRepository.save(existingLesson);
            }).orElseThrow(() -> new RuntimeException("Lesson not found with id: " + id));

    }

    public List<Lesson> generateBy(LessonDescriptor lessonDescriptor, MonthType monthType) {
        LessonDayType dayType = lessonDescriptor.getDayType();

        List<Lesson> list = getEvenDays(monthType, 2025, dayType)
            .stream()
            .map(day -> {
                Lesson lesson = new Lesson();
                lesson.setLessonDescriptor(lessonDescriptor);
                lesson.setData(day);
                lesson.setCompleted(false);
                return lesson;
            }).toList();

        return lessonRepository.saveAll(list);

    }

    public List<LocalDateTime> getEvenDays(MonthType monthName, int year, LessonDayType dayTyp) {
        try {
            Month month = Month.valueOf(monthName.name());
            int daysInMonth = YearMonth.of(year, month).lengthOfMonth();

            return IntStream.rangeClosed(1, daysInMonth)
                .filter(day -> isValidLessonDay(year, month, day, dayTyp))
                .mapToObj(day -> LocalDateTime.of(year, month, day, 0, 0, 0))
                .collect(Collectors.toList());

        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: некорректное имя месяца.");
            return Collections.emptyList();
        }
    }

    private boolean isValidLessonDay(int year, Month month, int day, LessonDayType dayType) {
        DayOfWeek dayOfWeek = LocalDate.of(year, month, day).getDayOfWeek();

        if (dayType == LessonDayType.EVEN_DAY) {
            return dayOfWeek == DayOfWeek.TUESDAY || dayOfWeek == DayOfWeek.THURSDAY || dayOfWeek == DayOfWeek.SATURDAY;
        } else {
            return dayOfWeek == DayOfWeek.MONDAY || dayOfWeek == DayOfWeek.WEDNESDAY || dayOfWeek == DayOfWeek.FRIDAY;
        }
    }

    public List<Lesson> generateByDate(List<Lesson> lessonDescriptorList) {
        return lessonRepository.saveAll(lessonDescriptorList);
    }

    @Transactional
    public Lesson completed(Long lessonId) {
        Optional<Lesson> existingLesson = getLessonById(lessonId);
        return existingLesson
            .map(lesson -> {
                lesson.setCompleted(true);
                lessonRepository.save(lesson);

                return lesson;
            })
            .orElse(null);

    }
//
//    @Transactional
//    public LessonInfoDto groupCompleted(Long lessonId, List<Long> noPresentsIds) {
//        List<StudentLesson> studentLessons = studentLessonRepository.findByLessonId(lessonId).stream()
//            .filter(studentLesson -> noPresentsIds.contains(studentLesson.getStudentId()))
//            .peek(studentLesson -> studentLesson.setPresent(false))
//            .collect(Collectors.toList());
//
//        studentLessonRepository.saveAll(studentLessons);
//
//        List<Student> noPresentStudents = studentRepository.findAllById(noPresentsIds);
//
//        LessonInfoDto lessonInfoDto = updateLesson(lessonId, noPresentStudents);
//        return lessonInfoDto;
//    }
//
//    private LessonInfoDto updateLesson(Long lessonId, List<Student> noPresentStudents) {
//        Lesson existingLesson = getLessonById(lessonId);
//        existingLesson.setCompleted(true);
//        Lesson savedLesson = lessonRepository.save(existingLesson);
//
//        LessonInfoDto lessonInfoDto = new LessonInfoDto();
//        lessonInfoDto.setLesson(savedLesson);
//        lessonInfoDto.setNoPresentStudents(noPresentStudents);
//        return lessonInfoDto;
//    }

    public void attach(Long lessonId, Long lessonDescriptorId) {
        Optional<Lesson> byId = lessonRepository.findById(lessonId);
        if (byId.isEmpty()) {
            throw new RuntimeException("Lesson not found with id: " + lessonId);
        }

        Lesson lesson = byId.get();

        Optional<LessonDescriptor> byId1 = lessonDescriptorRepository.findById(lessonDescriptorId);
        if (byId1.isEmpty()) {
            throw new RuntimeException("LessonDescriptor not found with id: " + lessonDescriptorId);
        }
        lesson.setLessonDescriptor(byId1.get());
        lessonRepository.save(lesson);

    }

}

