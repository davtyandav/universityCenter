package com.davdavtyan.universitycenter.controller;

import com.davdavtyan.universitycenter.converter.FileConvertor;
import com.davdavtyan.universitycenter.dto.response.FileResponse;
import com.davdavtyan.universitycenter.entity.FileEntity;
import com.davdavtyan.universitycenter.service.FileService;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/files")
public class FileController {

    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<InputStreamResource> getFile(@PathVariable Long id) throws FileNotFoundException {
        FileEntity fileEntity = fileService.getFileEntityById(id);
        InputStreamResource resource = new InputStreamResource(fileService.getFile(fileEntity));

        return ResponseEntity.ok()
            .contentLength(fileEntity.getSize())
            .contentType(MediaType.parseMediaType(fileEntity.getContentType()))
            .header("Content-Disposition", "attachment; filename=\"" + fileEntity.getOriginalName() + "\"")
            .body(resource);
    }

    @PostMapping("/upload")
    public ResponseEntity<FileResponse> uploadFile(@RequestParam("file") MultipartFile file) {
        System.out.println("Received file: " + file.getOriginalFilename());
        try {
            FileEntity savedFile = fileService.saveFile(file);
            FileResponse response = FileConvertor.toDto(savedFile);
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(new FileResponse());
        }
    }


    @GetMapping("/all")
    public ResponseEntity<List<FileResponse>> getAllFiles() {
        List<FileEntity> files = fileService.getAllFiles();
        List<FileResponse> responses = files.stream()
            .map(FileConvertor::toDto)
            .toList();
        return ResponseEntity.ok(responses);
    }

}
