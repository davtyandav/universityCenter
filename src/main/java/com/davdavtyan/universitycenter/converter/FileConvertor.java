package com.davdavtyan.universitycenter.converter;

import com.davdavtyan.universitycenter.dto.response.FileResponse;
import com.davdavtyan.universitycenter.entity.FileEntity;

public class FileConvertor {

    public static FileResponse toDto(FileEntity file) {
        FileResponse fileResponse = new FileResponse();
        fileResponse.setId(file.getId());
        fileResponse.setFilePath(file.getFilePath());
        fileResponse.setOriginalName(file.getOriginalName());
        fileResponse.setStorageName(file.getStorageName());
        fileResponse.setSize(file.getSize());

        return fileResponse;
    }

}
