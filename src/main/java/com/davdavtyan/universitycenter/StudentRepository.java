package com.davdavtyan.universitycenter;

import com.davdavtyan.universitycenter.entity.Mentor;
import com.davdavtyan.universitycenter.entity.Student;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    List<Student> findStudentsByMentorId(Long mentorId);

    Optional<Student> findByUserId(Long userId);

    List<Student> findByLessonDescriptorId(Long lessonDescriptorId);

    List<Student> findAllByIdIn(List<Long> ids);
}

