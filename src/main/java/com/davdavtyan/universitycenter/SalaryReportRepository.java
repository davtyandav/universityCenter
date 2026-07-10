package com.davdavtyan.universitycenter;

import com.davdavtyan.universitycenter.entity.SalaryReportEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalaryReportRepository extends JpaRepository<SalaryReportEntity, Long> {

    List<SalaryReportEntity> findAllByMentor_IdOrderByGeneratedAtDesc(Long mentorId);

}