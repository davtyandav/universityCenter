package com.davdavtyan.universitycenter.controller;

import com.davdavtyan.universitycenter.StudentRepository;
import com.davdavtyan.universitycenter.converter.StudentConverter;
import com.davdavtyan.universitycenter.dto.request.StudentRequest;
import com.davdavtyan.universitycenter.dto.response.StudentResponse;
import com.davdavtyan.universitycenter.entity.Student;
import com.davdavtyan.universitycenter.service.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/students")
public class StudentController {

    private final StudentService studentService;
    private final StudentRepository studentRepository;

    public StudentController(StudentService studentService, StudentRepository studentRepository) {
        this.studentService = studentService;
        this.studentRepository = studentRepository;
    }

    @GetMapping
    public ResponseEntity<List<StudentResponse>> getAllStudents() {
        List<StudentResponse> students = studentService.getAllStudents()
            .stream()
            .map(StudentConverter::toDto)
            .toList();
        return ResponseEntity.ok(students);
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
        Student createdStudent = studentService.createStudent(student, studentRequest.getMentorId());
        StudentResponse dto = StudentConverter.toDto(createdStudent);
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long id, @RequestBody StudentRequest studentDetails) {
        try {
            Student student = StudentConverter.toEntity(studentDetails);

            Student updatedStudent = studentService.updateStudent(id, student, studentDetails.getMentorId());
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

}
