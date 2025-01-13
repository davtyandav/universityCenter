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
import java.util.Objects;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService {

    @Value("${upload.path}")
    private Path uploadPath;

    private final FileRepository fileRepository;

    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public String saveFile(MultipartFile file) throws IOException {
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String filePath = uploadPath.resolve(Objects.requireNonNull(file.getOriginalFilename())).toString();
        file.transferTo(Path.of(filePath));

        FileEntity fileEntity = new FileEntity();
        fileEntity.setFilePath(filePath);
        fileRepository.save(fileEntity);

        return filePath;
    }

    public InputStream getFileById(Long id) throws FileNotFoundException {
        return fileRepository.findById(id)
            .map(fileEntity -> Paths.get(fileEntity.getFilePath()).toFile())
            .filter(File::exists)
            .map(file -> {
                try {
                    return new FileInputStream(file);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException("Файл не найден: " + file.getPath(), e);
                }
            })
            .orElseThrow(() -> new FileNotFoundException("Файл не найден: " + id));
    }

}
