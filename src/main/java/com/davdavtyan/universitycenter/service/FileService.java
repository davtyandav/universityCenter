package com.davdavtyan.universitycenter.service;

import com.davdavtyan.universitycenter.FileRepository;
import com.davdavtyan.universitycenter.entity.FileEntity;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService {

    @Value("${upload.path}")
    private String uploadDir;

    private final FileRepository fileRepository;

    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public FileEntity saveFile(MultipartFile file) throws IOException {
        Path dirPath = Paths.get(uploadDir);
        if (!Files.exists(dirPath)) {
            Files.createDirectories(dirPath);
        }

        String originalName = Objects.requireNonNull(file.getOriginalFilename());
        originalName = sanitizeFileName(originalName);
        String uniqueFileName = generateUniqueFileName(originalName);
        Path filePath = dirPath.resolve(uniqueFileName);

        file.transferTo(filePath);

        FileEntity fileEntity = new FileEntity();
        fileEntity.setOriginalName(originalName);
        fileEntity.setStorageName(uniqueFileName);
        fileEntity.setFilePath(filePath.toString());
        fileEntity.setContentType(file.getContentType());
        fileEntity.setSize(file.getSize());

        return fileRepository.save(fileEntity);
    }

    public InputStream getFile(FileEntity fileEntity) {
        File file = Paths.get(fileEntity.getFilePath()).toFile();
        if (!file.exists()) {
            throw new RuntimeException("Файл не найден: " + file.getPath());
        }
        try {
            return new FileInputStream(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Файл не найден: " + file.getPath(), e);
        }
    }

    public FileEntity getFileEntityById(Long id) throws FileNotFoundException {
        return fileRepository.findById(id)
            .orElseThrow(() -> new FileNotFoundException("Файл не найден: " + id));
    }

    public List<FileEntity> getAllFiles() {
        return fileRepository.findAll();
    }

    private String generateUniqueFileName(String originalName) {
        String ext = "";
        int dotIndex = originalName.lastIndexOf('.');
        if (dotIndex > 0) {
            ext = originalName.substring(dotIndex);
        }
        return System.currentTimeMillis() + "_" + java.util.UUID.randomUUID() + ext;
    }

    private String sanitizeFileName(String fileName) {
        return fileName.replaceAll("[^a-zA-Z0-9\\.\\-_]", "_");
    }

}
