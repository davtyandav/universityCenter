package com.davdavtyan.universitycenter;

import com.davdavtyan.universitycenter.entity.LessonDescriptor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LessonDescriptorRepository extends JpaRepository<LessonDescriptor, Long> {

}
