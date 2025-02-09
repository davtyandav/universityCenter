package com.davdavtyan.universitycenter;

import com.davdavtyan.universitycenter.entity.Lesson;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {
    List<Lesson> findByStudents_Id(Long studentId);
}
