package com.davdavtyan.universitycenter;

import com.davdavtyan.universitycenter.entity.Student;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    List<Student> findStudentsByMentorId(Long mentorId);

}

