package com.davdavtyan.universitycenter.dto.request;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentAssignmentDto {
    private List<Long> studentIds;
}