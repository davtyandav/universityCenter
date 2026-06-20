package com.davdavtyan.universitycenter.service;

import com.davdavtyan.universitycenter.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    List<Attendance> findByLessonId(Long lessonId);
    List<Attendance> findByStudentId(Long studentId);
}