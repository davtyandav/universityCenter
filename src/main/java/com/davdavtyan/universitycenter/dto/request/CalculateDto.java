package com.davdavtyan.universitycenter.dto.request;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CalculateDto {
    private Long mentorId;
    private LocalDateTime start;
    private LocalDateTime end;

}