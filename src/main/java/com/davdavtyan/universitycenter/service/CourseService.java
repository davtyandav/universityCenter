package com.davdavtyan.universitycenter.service;

import com.davdavtyan.universitycenter.CourseRepository;
import com.davdavtyan.universitycenter.entity.Course;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;

    @Transactional
    public Course createCourse(Course courseDto) {
        if (courseRepository.findByTitle(courseDto.getTitle()).isPresent()) {
            throw new IllegalArgumentException("Курс с таким названием уже существует!");
        }

        Course course = Course.builder()
            .title(courseDto.getTitle())
            .build();

        return courseRepository.save(course);
    }

    public List<Course> findAll() {
        return courseRepository.findAll();
    }

}