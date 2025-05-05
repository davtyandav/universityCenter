package com.davdavtyan.universitycenter;

import com.davdavtyan.universitycenter.entity.Mentor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MentorRepository extends JpaRepository<Mentor, Long> {

}

