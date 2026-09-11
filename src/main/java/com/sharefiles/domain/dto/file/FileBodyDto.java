package com.sharefiles.domain.dto.file;

import lombok.Builder;

@Builder
public record FileBodyDto(
        String fileName,
        String length,
        String typeFile
) {
}
