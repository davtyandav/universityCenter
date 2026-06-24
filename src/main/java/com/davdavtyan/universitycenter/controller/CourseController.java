package com.davdavtyan.universitycenter.controller;

import com.davdavtyan.universitycenter.entity.Course;
import com.davdavtyan.universitycenter.service.CourseService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/courses")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class CourseController {

    private final CourseService courseService;

    @PostMapping
//    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> createCourse(@RequestBody Course courseDto) {
        try {
            Course savedCourse = courseService.createCourse(courseDto);
            return new ResponseEntity<>(savedCourse, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ошибка при создании курса");
        }
    }

    @GetMapping
    public ResponseEntity<List<Course>> getAllCourses() {
        return ResponseEntity.ok(courseService.findAll());
    }

}