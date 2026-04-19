package com.davdavtyan.universitycenter.controller;

import com.davdavtyan.universitycenter.converter.StudentConverter;
import com.davdavtyan.universitycenter.dto.request.AttachRequest;
import com.davdavtyan.universitycenter.dto.request.StudentAssignmentDto;
import com.davdavtyan.universitycenter.dto.request.StudentRequest;
import com.davdavtyan.universitycenter.dto.response.LessonStudentsResponse;
import com.davdavtyan.universitycenter.dto.response.StudentResponse;
import com.davdavtyan.universitycenter.entity.Student;
import com.davdavtyan.universitycenter.entity.User;
import com.davdavtyan.universitycenter.service.StudentService;
import com.davdavtyan.universitycenter.service.UserService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/students")
public class StudentController {

    private final StudentService studentService;
    private final UserService userService;

    public StudentController(StudentService studentService, UserService userService) {
        this.studentService = studentService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<StudentResponse>> getAllStudents() {
        List<StudentResponse> students = studentService.getAllStudents()
            .stream()
            .map(StudentConverter::toDto)
            .toList();
        return ResponseEntity.ok(students);
    }

    @GetMapping("/lessonDescriptor/{descriptorId}/lesson/{lessonId}")
    public ResponseEntity<LessonStudentsResponse> getStudentsByLessonDescriptor(@PathVariable Long descriptorId,
                                                                                @PathVariable Long lessonId) {
        LessonStudentsResponse lessonWithStudents = studentService.getLessonWithStudents(descriptorId, lessonId);

        return ResponseEntity.ok(lessonWithStudents);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentResponse> getStudentById(@PathVariable Long id) {
        return studentService.getStudentById(id)
            .map(StudentConverter::toDto)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<StudentResponse> createStudent(@RequestBody StudentRequest studentRequest) {
        Student student = StudentConverter.toEntity(studentRequest);
        User user = userService.getUserById(studentRequest.getUserId())
            .orElseThrow(() -> new RuntimeException("User not found"));
        student.setUser(user);
        Student createdStudent = studentService.createStudent(student, studentRequest.getMentorId());
        StudentResponse dto = StudentConverter.toDto(createdStudent);
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long id, @RequestBody StudentRequest studentDetails) {
        try {

            Student updatedStudent = studentService.updateStudent(id, studentDetails);
            return ResponseEntity.ok(updatedStudent);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/attach")
    public ResponseEntity<List<StudentResponse>> attach(@RequestBody AttachRequest attachRequest) {
        List<Long> studentsIds = attachRequest.getStudentsIds();
        Long mentorId = attachRequest.getMentorId();

        List<StudentResponse> studentResponseList = studentService.attach(mentorId, studentsIds)
            .stream()
            .map(StudentConverter::toDto)
            .toList();

        return ResponseEntity.ok(studentResponseList);
    }

    // POST http://localhost:8080/api/v1/students/lessonDescriptor/5
    @PostMapping("/lessonDescriptor/{descriptorId}")
    public ResponseEntity<?> updateDescriptor(@PathVariable Long descriptorId,
                                              @RequestBody StudentAssignmentDto dto) {

        studentService.updateStudentsDescriptor(descriptorId, dto.getStudentIds());
        return ResponseEntity.ok("Students updated successfully");
    }

}
