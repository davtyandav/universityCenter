package com.davdavtyan.universitycenter.dto.response;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class StudentResponse {

    private Long id;
    private UserResponse user;
    private Date birthDate;
    private MentorResponse mentor;
    private LessonDescriptorResponse lessonDescriptor;

}
