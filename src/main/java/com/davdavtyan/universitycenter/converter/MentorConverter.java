package com.davdavtyan.universitycenter.converter;

import com.davdavtyan.universitycenter.dto.request.MentorRequest;
import com.davdavtyan.universitycenter.dto.request.UserRequest;
import com.davdavtyan.universitycenter.dto.response.MentorResponse;
import com.davdavtyan.universitycenter.entity.Mentor;
import com.davdavtyan.universitycenter.entity.User;
import org.springframework.stereotype.Component;

@Component
public class MentorConverter {

    public static Mentor toEntity(MentorRequest mentorRequest) {
        Mentor mentor = new Mentor();
        mentor.setBirthDate(mentorRequest.getBirthDate());
        return mentor;
    }

    public static MentorResponse toDto(Mentor mentor) {
        MentorResponse mentorResponse = new MentorResponse();
        mentorResponse.setId(mentor.getId());
        mentorResponse.setUser(UserConverter.toDto(mentor.getUser()));
        return mentorResponse;
    }

    public static MentorResponse toDtoMentorInfo(Mentor mentor) {
        MentorResponse mentorResponse = toDto(mentor);
        mentorResponse.setBirthDate(mentor.getBirthDate());
        mentorResponse.setUser(UserConverter.toDto(mentor.getUser()));
        mentorResponse.setStudents(mentor.getStudents().stream().map(StudentConverter::toDto).toList());
        mentorResponse.setLessonDescriptors(
            mentor.getLessonDescriptors().stream().map(LessonDescriptorConverter::toDto).toList());
        return mentorResponse;
    }

}
