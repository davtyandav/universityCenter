package com.davdavtyan.universitycenter.service;

import com.davdavtyan.universitycenter.LessonRepository;
import com.davdavtyan.universitycenter.StudentRepository;
import com.davdavtyan.universitycenter.entity.Lesson;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LessonService {

    private final LessonRepository lessonRepository;
    private final StudentRepository studentRepository;

    @Autowired
    public LessonService(LessonRepository lessonRepository, StudentRepository studentRepository) {
        this.lessonRepository = lessonRepository;
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

    public List<Lesson> getLessonByStudentId(Long id) {
        return lessonRepository.findByStudents_Id(id);
    }

    public List<Lesson> generate(List<Lesson> lessonList) {
        return lessonRepository.saveAll(lessonList);
    }

//    @Transactional
//    public LessonCompetedResponse completed(Long lessonId, Long studentId) {
//        Lesson existingLesson = getLessonById(lessonId);
//        existingLesson.setCompleted(true);
//        lessonRepository.save(existingLesson);
//
//        Student student = studentRepository.findById(studentId)
//            .orElseThrow(() -> new RuntimeException("Student not found with id: " + studentId));
//
//        int countCompletedDay = student.getCountCompletedDay() + 1;
//        student.setCountCompletedDay(countCompletedDay);
//        studentRepository.save(student);
//
//        LessonCompetedResponse response = new LessonCompetedResponse();
//        response.setStudentName(student.getName());
//        response.setData(existingLesson.getData());
//        return response;
//    }
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

    public void attach(Long lessonId, List<Long> studentIds) {
        Optional<Lesson> byId = lessonRepository.findById(lessonId);
        if (byId.isEmpty()) {
            throw new RuntimeException("Lesson not found with id: " + lessonId);
        }

        Lesson lesson = byId.get();

        lesson.setStudents(studentRepository.findAllById(studentIds));
        lessonRepository.save(lesson);

    }
}

