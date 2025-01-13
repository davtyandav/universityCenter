package com.davdavtyan.universitycenter;

import com.davdavtyan.universitycenter.entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface FileRepository extends JpaRepository<FileEntity, Long> {
    Optional<FileEntity> findByFilePath(String filePath);
}
