package com.sharefiles.domain.mapper;

import com.sharefiles.db.entity.FileEntity;
import com.sharefiles.domain.dto.file.FileBodyDto;
import org.springframework.stereotype.Component;

@Component
public class FileMapper {

    public FileEntity toEntity(FileBodyDto fileBodyDto, String codeUsedToCallback, String urlBucket) {
        return FileEntity.builder()
                .fileName(fileBodyDto.fileName())
                .lengthFile(fileBodyDto.length())
                .typeFile(fileBodyDto.typeFile())
                .codeUsedToCallback(codeUsedToCallback)
                .urlBucket(urlBucket)
                .createdAt(java.time.LocalDateTime.now())
                .build();

    }
}
