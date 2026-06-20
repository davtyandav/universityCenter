package com.davdavtyan.universitycenter.dto.request;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class AssignmentRequest {
    private String title;
    private String description;
    private LocalDateTime deadline;
    private Long fileId; // ID прикрепленного файла, если есть
}