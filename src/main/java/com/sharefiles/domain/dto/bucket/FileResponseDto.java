package com.sharefiles.domain.dto.bucket;

import lombok.Builder;

@Builder
public record FileResponseDto(
        String fileName,
        String contentType,
        byte[] content
) {}
