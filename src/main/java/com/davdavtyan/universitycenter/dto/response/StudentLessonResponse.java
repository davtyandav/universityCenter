package com.davdavtyan.universitycenter.dto.response;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class StudentLessonResponse {
    private Long id;
    private String name;
    private String lastName;
    private String email;
    private Date birthDate;

}
