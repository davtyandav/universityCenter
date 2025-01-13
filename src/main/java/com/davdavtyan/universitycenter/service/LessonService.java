package com.davdavtyan.universitycenter.service;

import com.davdavtyan.universitycenter.LessonDescriptorRepository;
import com.davdavtyan.universitycenter.LessonRepository;
import com.davdavtyan.universitycenter.StudentRepository;
import com.davdavtyan.universitycenter.entity.Lesson;
import com.davdavtyan.universitycenter.entity.LessonDescriptor;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LessonService {

    private final LessonRepository lessonRepository;
    private final LessonDescriptorRepository lessonDescriptorRepository;
    private final StudentRepository studentRepository;

    @Autowired
    public LessonService(LessonRepository lessonRepository, LessonDescriptorRepository lessonDescriptorRepository, StudentRepository studentRepository) {
        this.lessonRepository = lessonRepository;
        this.lessonDescriptorRepository = lessonDescriptorRepository;
        this.studentRepository = studentRepository;
    }

    public Lesson addLesson(Lesson lessonDescriptor) {
        return lessonRepository.save(lessonDescriptor);
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
//                existingLesson.setType(lesson.getType());
               // existingLesson.setCompleted(lesson.isCompleted());
                return lessonRepository.save(existingLesson);
            }).orElseThrow(() -> new RuntimeException("Lesson not found with id: " + id));

    }

    public List<Lesson> getLessonByStudentId(Long id) {
        return null;
    }

    public List<Lesson> generate(List<Lesson> lessonDescriptorList) {
        return lessonRepository.saveAll(lessonDescriptorList);
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

