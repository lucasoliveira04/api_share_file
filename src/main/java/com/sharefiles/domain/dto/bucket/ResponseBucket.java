package com.sharefiles.domain.dto.bucket;

import lombok.Builder;

@Builder
public record ResponseBucket(
        String message,
        String timestamp
) {
}
