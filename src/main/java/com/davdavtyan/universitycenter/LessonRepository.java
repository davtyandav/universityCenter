package com.davdavtyan.universitycenter;

import com.davdavtyan.universitycenter.entity.Lesson;
import com.davdavtyan.universitycenter.entity.Student;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {
    List<Lesson> findLessonsByStudentId(Long id);

}
