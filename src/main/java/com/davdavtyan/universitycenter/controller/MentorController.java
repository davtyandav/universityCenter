package com.davdavtyan.universitycenter.controller;

import com.davdavtyan.universitycenter.converter.MentorConverter;
import com.davdavtyan.universitycenter.converter.StudentConverter;
import com.davdavtyan.universitycenter.dto.request.MentorRequest;
import com.davdavtyan.universitycenter.dto.response.MentorResponse;
import com.davdavtyan.universitycenter.dto.response.StudentResponse;
import com.davdavtyan.universitycenter.entity.Mentor;
import com.davdavtyan.universitycenter.service.MentorService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/mentors")
public class MentorController {

    private final MentorService mentorService;
    public MentorController(MentorService mentorService) {
        this.mentorService = mentorService;
    }

    @GetMapping
    public ResponseEntity<List<MentorResponse>> getAllMentors() {
        List<MentorResponse> mentors = mentorService.getAllMentors()
            .stream()
            .map(MentorConverter::toDto)
            .toList();

        return ResponseEntity.ok(mentors);
    }

    @PostMapping
    public ResponseEntity<MentorResponse> createMentor(@RequestBody MentorRequest mentorRequest) {
        Mentor mentor = MentorConverter.toEntity(mentorRequest);
        Mentor serviceMentor = mentorService.createMentor(mentor);
        MentorResponse dto = MentorConverter.toDto(serviceMentor);

        return ResponseEntity.ok(dto);
    }


    @GetMapping("/{id}")
    public ResponseEntity<MentorResponse> getStudentById(@PathVariable Long id) {
        return mentorService.getMentorById(id)
            .map(MentorConverter::toDto)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }


}
