package com.sharefiles.domain.dto.bucket;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public abstract class BucketResponseAbstraction {
    private String timestamp;
    private String status;
    private String bucketName;
}
