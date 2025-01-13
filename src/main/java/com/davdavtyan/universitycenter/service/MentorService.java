package com.davdavtyan.universitycenter.service;

import com.davdavtyan.universitycenter.MentorRepository;
import com.davdavtyan.universitycenter.entity.Mentor;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class MentorService {

    private final MentorRepository mentorRepository;

    public MentorService(MentorRepository mentorRepository) {
        this.mentorRepository = mentorRepository;
    }


    public List<Mentor> getAllMentors() {
        return mentorRepository.findAll();
    }

    public Optional<Mentor> getMentorById(Long id) {
        return mentorRepository.findById(id);
    }

    public Mentor createMentor(Mentor student) {
        return mentorRepository.save(student);
    }

    public Mentor updateMentor(Long id, Mentor studentDetails) {
        return mentorRepository.findById(id)
            .map(student -> {
                student.setName(studentDetails.getName());
                student.setLastName(studentDetails.getLastName());
                student.setEmail(studentDetails.getEmail());
                student.setBirthDate(studentDetails.getBirthDate());
                return mentorRepository.save(student);
            })
            .orElseThrow(() -> new IllegalArgumentException("Mentor not found with id: " + id));
    }

    public void deleteMentor(Long id) {
        mentorRepository.deleteById(id);
    }
}
