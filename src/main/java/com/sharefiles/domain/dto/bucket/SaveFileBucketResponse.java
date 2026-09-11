package com.sharefiles.domain.dto.bucket;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class SaveFileBucketResponse extends BucketResponseAbstraction {
    private String keyFile;
    private String fileName;
    private String lengthFile;
    private String typeFile;
}
