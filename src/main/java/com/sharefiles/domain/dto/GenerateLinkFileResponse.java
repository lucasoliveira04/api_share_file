package com.sharefiles.domain.dto;

import lombok.Builder;

@Builder
public record GenerateLinkFileResponse(
        String linkCallback,
        String codeCallback
) {}
