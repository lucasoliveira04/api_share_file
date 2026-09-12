package com.sharefiles.domain.service;

import com.sharefiles.domain.dto.GenerateLinkFileResponse;
import com.sharefiles.domain.service.bucket.IBucket;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileService {

    private final IBucket bucket;

    public GenerateLinkFileResponse generateLinkFile(MultipartFile file) {
        // TODO should generate link of file
        return null;
    }
}
