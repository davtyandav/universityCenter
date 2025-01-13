package com.davdavtyan.universitycenter.service;

import com.davdavtyan.universitycenter.LessonDescriptorRepository;
import com.davdavtyan.universitycenter.entity.LessonDescriptor;
import com.davdavtyan.universitycenter.entity.Student;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class LessonDescriptorService {

    private final LessonDescriptorRepository lessonDescriptorRepository;

    public LessonDescriptorService(LessonDescriptorRepository lessonDescriptorRepository) {
        this.lessonDescriptorRepository = lessonDescriptorRepository;
    }

    public List<LessonDescriptor> getAllLessonDescriptor() {
        return lessonDescriptorRepository.findAll();
    }

    public LessonDescriptor getLessonDescriptor(Long id) {
        return lessonDescriptorRepository.findById(id).get();
    }

}
