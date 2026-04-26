package com.davdavtyan.universitycenter;

import com.davdavtyan.universitycenter.entity.Student;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    @Query("SELECT s FROM students s " +
        "LEFT JOIN FETCH s.mentor " +
        "LEFT JOIN FETCH s.lessonDescriptor " +
        "WHERE (:hasMentor IS NULL OR (:hasMentor = true AND s.mentor IS NOT NULL) OR (:hasMentor = false AND s.mentor IS NULL)) " +
        "AND (:hasDescriptor IS NULL OR (:hasDescriptor = true AND s.lessonDescriptor IS NOT NULL) OR (:hasDescriptor = false AND s.lessonDescriptor IS NULL))")
    List<Student> findFilteredWithRelations(
        @Param("hasMentor") Boolean hasMentor,
        @Param("hasDescriptor") Boolean hasDescriptor
    );

    List<Student> findStudentsByMentorId(Long mentorId);

    Optional<Student> findByUserId(Long userId);

    List<Student> findByLessonDescriptorId(Long lessonDescriptorId);

    List<Student> findAllByIdIn(List<Long> ids);
}

