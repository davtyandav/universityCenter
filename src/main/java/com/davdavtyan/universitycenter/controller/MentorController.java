package com.davdavtyan.universitycenter.controller;

import com.davdavtyan.universitycenter.converter.MentorConverter;
import com.davdavtyan.universitycenter.dto.request.MentorRequest;
import com.davdavtyan.universitycenter.dto.response.MentorResponse;
import com.davdavtyan.universitycenter.entity.Mentor;
import com.davdavtyan.universitycenter.entity.User;
import com.davdavtyan.universitycenter.service.MentorService;
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
@RequestMapping("/api/v1/mentors")
public class MentorController {

    private final MentorService mentorService;
    private final UserService userService;

    public MentorController(MentorService mentorService, UserService userService) {
        this.mentorService = mentorService;
        this.userService = userService;
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
        User user = userService.getUserById(mentorRequest.getUserId())
            .orElseThrow(() -> new RuntimeException("User not found"));

        Mentor mentor = MentorConverter.toEntity(mentorRequest);
        mentor.setUser(user);

        Mentor savedMentor = mentorService.createMentor(mentor);
        return ResponseEntity.ok(MentorConverter.toDto(savedMentor));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MentorResponse> mentorsInfo(@PathVariable Long id) {
        return mentorService.getMentorById(id)
            .map(MentorConverter::toDtoMentorInfo)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<MentorResponse> updateMentor(@PathVariable Long id,
                                                       @RequestBody MentorRequest mentorRequest) {
        try {

            Mentor entity = MentorConverter.toEntity(mentorRequest);
            Mentor updateMentor = mentorService.updateMentor(id, entity);
            MentorResponse dto = MentorConverter.toDto(updateMentor);
            return ResponseEntity.ok(dto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMentor(@PathVariable Long id) {
        mentorService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }

}
