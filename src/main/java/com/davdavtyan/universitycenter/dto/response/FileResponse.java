package com.davdavtyan.universitycenter.dto.response;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FileResponse {

    private Long id;

    private String filePath;

    private String originalName;

    private String storageName;

    private String contentType;

    private Long size;

}
