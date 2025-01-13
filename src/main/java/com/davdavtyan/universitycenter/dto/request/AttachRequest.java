package com.davdavtyan.universitycenter.dto.request;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AttachRequest {

    private Long mentorId;
    private List<Long> studentsIds;

}
