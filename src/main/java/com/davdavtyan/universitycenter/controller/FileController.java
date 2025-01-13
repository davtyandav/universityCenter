package com.davdavtyan.universitycenter.controller;

import com.davdavtyan.universitycenter.service.FileService;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/files")
public class FileController {

    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping(value = "/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    public InputStreamResource getFile(@PathVariable Long id) throws FileNotFoundException {
        return new InputStreamResource(fileService.getFileById(id));
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            String filePath = fileService.saveFile(file);
            return ResponseEntity.ok("Файл загружен: " + filePath);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Ошибка загрузки файла.");
        }
    }

}
