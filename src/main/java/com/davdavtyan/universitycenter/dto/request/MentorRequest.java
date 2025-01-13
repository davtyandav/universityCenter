package com.davdavtyan.universitycenter.dto.request;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MentorRequest {

    private Long id;
    private Long userId;
    private Date birthDate;

}
