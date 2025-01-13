package com.davdavtyan.universitycenter.service;

import com.davdavtyan.universitycenter.MentorRepository;
import com.davdavtyan.universitycenter.StudentRepository;
import com.davdavtyan.universitycenter.entity.Mentor;
import com.davdavtyan.universitycenter.entity.Student;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final MentorRepository mentorRepository;

    public StudentService(StudentRepository studentRepository, MentorRepository mentorRepository) {
        this.studentRepository = studentRepository;
        this.mentorRepository = mentorRepository;
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Optional<Student> getStudentById(Long id) {
        return studentRepository.findById(id);
    }

    public Student createStudent(Student student, Long mentorId) {
        if (mentorId != null) {
            Mentor mentor = mentorRepository.findById(mentorId)
                .orElseThrow(() -> new IllegalArgumentException("Mentor not found with id: " + mentorId));
            student.setMentor(mentor);
        }
        return studentRepository.save(student);
    }

    public Student updateStudent(Long id, Student studentDetails, Long mentorId) {
        return studentRepository.findById(id)
            .map(student -> {
                Mentor mentor = mentorRepository.findById(mentorId)
                    .orElseThrow(() -> new IllegalArgumentException("Mentor not found with id: " + mentorId));

                student.setName(studentDetails.getName());
                student.setLastName(studentDetails.getLastName());
                student.setEmail(studentDetails.getEmail());
                student.setBirthDate(studentDetails.getBirthDate());
                student.setMentor(mentor);
                return studentRepository.save(student);
            })
            .orElseThrow(() -> new IllegalArgumentException("Student not found with id: " + id));
    }

    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

}
