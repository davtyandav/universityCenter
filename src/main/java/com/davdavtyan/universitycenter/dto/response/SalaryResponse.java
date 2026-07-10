package com.davdavtyan.universitycenter.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SalaryResponse {
    private Double groupSalary;
    private Double singleSalary;
    private Double totalSalary;
}