package com.davdavtyan.universitycenter.service;

import com.davdavtyan.universitycenter.LessonDescriptorRepository;
import com.davdavtyan.universitycenter.LessonRepository;
import com.davdavtyan.universitycenter.MentorRepository;
import com.davdavtyan.universitycenter.StudentRepository;
import com.davdavtyan.universitycenter.converter.LessonConverter;
import com.davdavtyan.universitycenter.converter.StudentConverter;
import com.davdavtyan.universitycenter.dto.request.StudentRequest;
import com.davdavtyan.universitycenter.dto.response.LessonResponse;
import com.davdavtyan.universitycenter.dto.response.LessonStudentsResponse;
import com.davdavtyan.universitycenter.dto.response.StudentLessonResponse;
import com.davdavtyan.universitycenter.entity.Lesson;
import com.davdavtyan.universitycenter.entity.LessonDescriptor;
import com.davdavtyan.universitycenter.entity.Mentor;
import com.davdavtyan.universitycenter.entity.Student;
import com.davdavtyan.universitycenter.entity.User;
import com.davdavtyan.universitycenter.kafka.KafkaProducerService;
import com.kafka.avro.AssignmentNotification;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final LessonRepository lessonRepository;
    private final LessonDescriptorRepository lessonDescriptorRepository;
    private final MentorRepository mentorRepository;
    private final KafkaProducerService kafkaProducerService;

    public StudentService(StudentRepository studentRepository, LessonRepository lessonRepository,
                          LessonDescriptorRepository lessonDescriptorRepository,
                          MentorRepository mentorRepository,
                          KafkaProducerService kafkaProducerService) {
        this.studentRepository = studentRepository;
        this.lessonRepository = lessonRepository;
        this.lessonDescriptorRepository = lessonDescriptorRepository;
        this.mentorRepository = mentorRepository;
        this.kafkaProducerService = kafkaProducerService;
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public LessonStudentsResponse getLessonWithStudents(Long descriptorId, Long lessonId) {
        Lesson lesson = lessonRepository.findById(lessonId)
            .orElseThrow(() -> new EntityNotFoundException("Lesson not found"));

        List<StudentLessonResponse> students = studentRepository.findByLessonDescriptorId(descriptorId)
            .stream()
            .filter(s -> s.getLessonDescriptor().getId().equals(descriptorId))
            .map(StudentConverter::toStudentLessonRDto)
            .toList();

        LessonResponse dto = LessonConverter.toDto(lesson);
        LessonStudentsResponse lessonStudentsResponse = new LessonStudentsResponse();
        lessonStudentsResponse.setLesson(dto);
        lessonStudentsResponse.setStudents(students);
        return lessonStudentsResponse;
    }

    public Optional<Student> getStudentById(Long userId) {
        return studentRepository.findByUserId(userId);
    }

    public Optional<Student> getStudentUserById(Long userId) {
        return studentRepository.findByUserId(userId);
    }

    public Student createStudent(Student student, Long mentorId) {
        Mentor mentor = null;
        if (mentorId != null) {
            mentor = mentorRepository.findById(mentorId)
                .orElseThrow(() -> new IllegalArgumentException("Mentor not found with id: " + mentorId));
        }
        student.setMentor(mentor);
        student.setUser(student.getUser());

        Student save = studentRepository.save(student);

//        if (mentor != null) {
//            List<Student> attach = attach(mentor, Collections.singletonList(save));
//        }

        return save;
    }

    public List<Student> getFilteredStudents(Boolean hasMentor, Boolean hasDescriptor) {
        return studentRepository.findFilteredWithRelations(hasMentor, hasDescriptor);
    }

    public Student updateStudent(Long id, StudentRequest studentDetails) {
        Long mentorId = studentDetails.getMentorId();
        return studentRepository.findById(id)
            .map(student -> {
                Mentor mentor;
                if (mentorId != null) {
                    mentor = mentorRepository.findById(mentorId)
                        .orElseThrow(() -> new IllegalArgumentException("Mentor not found with id: " + mentorId));
//                    List<Student> attach = attach(mentor, List.of(student));
                } else {
                    mentor = null;
                }
                student.setBirthDate(studentDetails.getBirthDate());
                student.setMentor(mentor);
                student.setUser(student.getUser());
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

        User user = mentor.getUser();
        com.kafka.avro.Mentor mentorAvro = com.kafka.avro.Mentor.newBuilder()
            .setMentorEmail(user.getEmail())
            .setMentorName(user.getName())
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

    public void updateStudentsDescriptor(Long descriptorId, List<Long> studentIds) {
        LessonDescriptor descriptor = lessonDescriptorRepository.findById(descriptorId)
            .orElseThrow(() -> new RuntimeException("Descriptor not found"));

        List<Student> students = studentRepository.findAllByIdIn(studentIds);

        students.forEach(student -> {
            student.setLessonDescriptor(descriptor);
        });

        studentRepository.saveAll(students);
    }

    private com.kafka.avro.Student getStudent(Student student) {
        User user = student.getUser();
        return com.kafka.avro.Student.newBuilder()
            .setStudentEmail(user.getEmail())
            .setStudentName(user.getName())
            .build();
    }

}
