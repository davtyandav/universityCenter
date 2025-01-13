package com.davdavtyan.universitycenter.service;

import com.davdavtyan.universitycenter.MentorRepository;
import com.davdavtyan.universitycenter.StudentRepository;
import com.davdavtyan.universitycenter.entity.Mentor;
import com.davdavtyan.universitycenter.entity.Student;
import com.davdavtyan.universitycenter.kafka.KafkaProducerService;
import com.kafka.avro.AssignmentNotification;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final MentorRepository mentorRepository;
    private final KafkaProducerService kafkaProducerService;

    public StudentService(StudentRepository studentRepository, MentorRepository mentorRepository,
                          KafkaProducerService kafkaProducerService) {
        this.studentRepository = studentRepository;
        this.mentorRepository = mentorRepository;
        this.kafkaProducerService = kafkaProducerService;
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Optional<Student> getStudentById(Long id) {
        return studentRepository.findById(id);
    }

    public Student createStudent(Student student, Long mentorId) {
        Mentor mentor = null;
        if (mentorId != null) {
            mentor = mentorRepository.findById(mentorId)
                .orElseThrow(() -> new IllegalArgumentException("Mentor not found with id: " + mentorId));
        }
        student.setMentor(mentor);

        Student save = studentRepository.save(student);

//        if (mentor != null) {
//            List<Student> attach = attach(mentor, Collections.singletonList(save));
//        }

        return save;
    }

    public Student updateStudent(Long id, Student studentDetails, Long mentorId) {
        return studentRepository.findById(id)
            .map(student -> {
                Mentor mentor;
                if (mentorId != null) {
                    mentor = mentorRepository.findById(mentorId)
                        .orElseThrow(() -> new IllegalArgumentException("Mentor not found with id: " + mentorId));
                    List<Student> attach = attach(mentor, List.of(student));
                } else {
                    mentor = null;
                }

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

    public List<Student> attach(Long mentorId, List<Long> studentsIds) {
        Mentor mentor = mentorRepository.findById(mentorId)
            .orElseThrow(() -> new IllegalArgumentException("Mentor not found with id: " + mentorId));
        List<Student> studentsWithMentor = studentRepository.findAllById(studentsIds);
        return attach(mentor, studentsWithMentor);
    }

    private List<Student> attach(Mentor mentor, List<Student> students) {
        List<Student> studentsWithMentor = students.stream()
            .map(student -> {
                student.setMentor(mentor);
                return studentRepository.save(student);
            }).toList();

        com.kafka.avro.Mentor mentorAvro = com.kafka.avro.Mentor.newBuilder()
            .setMentorEmail(mentor.getEmail())
            .setMentorName(mentor.getName())
            .build();

        List<com.kafka.avro.Student> studentList = studentsWithMentor
            .stream()
            .map(this::getStudent)
            .toList();

        AssignmentNotification notification = AssignmentNotification.newBuilder()
            .setMentor(mentorAvro)
            .setStudents(studentList)
            .build();

        kafkaProducerService.sendAssignmentNotification(notification);

        return studentsWithMentor;
    }

    private com.kafka.avro.Student getStudent(Student student) {
        return com.kafka.avro.Student.newBuilder()
            .setStudentEmail(student.getEmail())
            .setStudentName(student.getName())
            .build();
    }

}
