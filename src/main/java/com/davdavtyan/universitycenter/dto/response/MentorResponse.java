package com.davdavtyan.universitycenter.dto.response;

import java.util.Date;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MentorResponse {

    private Long id;
    private UserResponse user;
    private Date birthDate;
    private List<StudentResponse> students;
    private List<LessonDescriptorResponse> lessonDescriptors;

}
