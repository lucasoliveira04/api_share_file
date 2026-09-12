package com.sharefiles.domain.dto.bucket;

import lombok.Builder;

@Builder
public record ResponseBucket(
        String keyImage,
        String timestamp,
        String codeCallback
) {
}
