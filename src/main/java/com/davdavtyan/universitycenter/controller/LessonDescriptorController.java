package com.davdavtyan.universitycenter.controller;

import com.davdavtyan.universitycenter.converter.LessonDescriptorConverter;
import com.davdavtyan.universitycenter.dto.response.LessonDescriptorResponse;
import com.davdavtyan.universitycenter.service.LessonDescriptorService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/lessonDescriptor")
public class LessonDescriptorController {

    private final LessonDescriptorService lessonDescriptorService;

    public LessonDescriptorController(LessonDescriptorService lessonDescriptorService) {
        this.lessonDescriptorService = lessonDescriptorService;
    }

    @GetMapping
    public ResponseEntity<List<LessonDescriptorResponse>> getAllStudents() {
        List<LessonDescriptorResponse> lessonResponses = lessonDescriptorService.getAllLessons()
            .stream()
            .map(LessonDescriptorConverter::toDto)
            .toList();
        return ResponseEntity.ok(lessonResponses);
    }

}
