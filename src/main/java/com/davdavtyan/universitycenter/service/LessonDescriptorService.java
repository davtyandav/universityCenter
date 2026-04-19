package com.davdavtyan.universitycenter.service;

import com.davdavtyan.universitycenter.LessonDescriptorRepository;
import com.davdavtyan.universitycenter.MentorRepository;
import com.davdavtyan.universitycenter.dto.response.MentorResponse;
import com.davdavtyan.universitycenter.entity.LessonDescriptor;
import com.davdavtyan.universitycenter.entity.Mentor;
import com.davdavtyan.universitycenter.entity.Student;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class LessonDescriptorService {

    private final LessonDescriptorRepository lessonDescriptorRepository;
    private final MentorRepository mentorRepository;

    public LessonDescriptorService(LessonDescriptorRepository lessonDescriptorRepository,
                                   MentorRepository mentorRepository) {
        this.lessonDescriptorRepository = lessonDescriptorRepository;
        this.mentorRepository = mentorRepository;
    }

    public List<LessonDescriptor> getAllLessonDescriptor() {
        return lessonDescriptorRepository.findAll();
    }

    public LessonDescriptor getLessonDescriptor(Long id) {
        return lessonDescriptorRepository.findById(id).get();
    }

    public LessonDescriptor createLessonDescriptor(LessonDescriptor entity, Long mentorId) {
        Mentor mentor = null;
        if (mentorId != null) {
            mentor = mentorRepository.findById(mentorId)
                .orElseThrow(() -> new IllegalArgumentException("Mentor not found with id: " + mentorId));
        }
        entity.setMentor(mentor);

        LessonDescriptor save = lessonDescriptorRepository.save(entity);

//        if (mentor != null) {
//            List<Student> attach = attach(mentor, Collections.singletonList(save));
//        }

        return save;
    }


}
