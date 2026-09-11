package com.sharefiles.domain.dto.file;

import lombok.Builder;
import org.springframework.web.multipart.MultipartFile;

@Builder
public record FileBodyDto(
        String fileName,
        String length,
        String typeFile,
        MultipartFile file
) {
}
