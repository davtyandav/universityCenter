package com.davdavtyan.universitycenter.converter;

import com.davdavtyan.universitycenter.dto.request.MentorRequest;
import com.davdavtyan.universitycenter.dto.response.MentorResponse;
import com.davdavtyan.universitycenter.entity.Mentor;

public class MentorConverter {

    public static Mentor toEntity(MentorRequest mentorRequest) {
        Mentor mentor = new Mentor();
        mentor.setName(mentorRequest.getName());
        mentor.setLastName(mentorRequest.getLastName());
        mentor.setBirthDate(mentorRequest.getBirthDate());
        mentor.setEmail(mentorRequest.getEmail());

        return mentor;
    }

    public static MentorResponse toDto(Mentor mentor) {
        MentorResponse mentorResponse = new MentorResponse();
        mentorResponse.setId(mentor.getId());
        mentorResponse.setName(mentor.getName());
        mentorResponse.setLastName(mentor.getLastName());
        mentorResponse.setBirthDate(mentor.getBirthDate());
        mentorResponse.setEmail(mentor.getEmail());

        return mentorResponse;
    }

}
