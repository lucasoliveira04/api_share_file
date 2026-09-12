package com.sharefiles.domain.service.bucket;

import com.sharefiles.domain.dto.bucket.ResponseBucket;
import com.sharefiles.domain.dto.file.FileBodyDto;

import java.io.IOException;

public interface IBucket {
    ResponseBucket save(FileBodyDto file) throws IOException;
    FileBodyDto getFile(String id);
    void delete(String id);
}
