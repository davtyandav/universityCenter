package com.davdavtyan.universitycenter.controller;

import com.davdavtyan.universitycenter.converter.LessonConverter;
import com.davdavtyan.universitycenter.dto.request.LessonRequest;
import com.davdavtyan.universitycenter.dto.response.LessonResponse;
import com.davdavtyan.universitycenter.entity.Lesson;
import com.davdavtyan.universitycenter.service.LessonService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/lessons")
public class LessonController {

    private final LessonService lessonService;

    public LessonController(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    @GetMapping
    public ResponseEntity<List<LessonResponse>> getAllStudents() {
        List<LessonResponse> lessonResponses = lessonService.getAllLessons()
            .stream()
            .map(LessonConverter::toDto)
            .toList();
        return ResponseEntity.ok(lessonResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LessonResponse> getLessonById(@PathVariable Long id) {
        return lessonService.getLessonById(id)
            .map(LessonConverter::toDto)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<LessonResponse> createLesson(@RequestBody LessonRequest lessonRequest) {
        Lesson lesson = LessonConverter.toEntity(lessonRequest);
        Lesson lessonResponse = lessonService.addLesson(lesson);
        LessonResponse dto = LessonConverter.toDto(lessonResponse);
        return ResponseEntity.ok(dto);
    }

}
