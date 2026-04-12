package com.davdavtyan.universitycenter;

import com.davdavtyan.universitycenter.entity.Mentor;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MentorRepository extends JpaRepository<Mentor, Long> {
    Optional<Mentor> findByUserId(Long userId);
}

