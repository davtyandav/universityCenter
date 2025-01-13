package com.davdavtyan.universitycenter.service;

import com.davdavtyan.universitycenter.LessonDescriptorRepository;
import com.davdavtyan.universitycenter.entity.LessonDescriptor;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class LessonDescriptorService {

    private final LessonDescriptorRepository lessonDescriptorRepository;

    public LessonDescriptorService(LessonDescriptorRepository lessonDescriptorRepository) {
        this.lessonDescriptorRepository = lessonDescriptorRepository;
    }

    public List<LessonDescriptor> getAllLessons() {
        return lessonDescriptorRepository.findAll();
    }

}
