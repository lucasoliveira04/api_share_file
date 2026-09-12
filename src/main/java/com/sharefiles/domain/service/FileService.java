package com.sharefiles.domain.service;

import com.sharefiles.domain.dto.GenerateLinkFileResponse;
import com.sharefiles.domain.dto.bucket.ResponseBucket;
import com.sharefiles.domain.dto.file.FileBodyDto;
import com.sharefiles.domain.service.bucket.IBucket;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileService {

    @Value("${callback.url}")
    private String linkCallback;
    private final IBucket bucket;

    public GenerateLinkFileResponse generateLinkFile(MultipartFile file) throws IOException {
        FileBodyDto fileBodyDto = FileBodyDto
                .builder()
                .fileName(file.getOriginalFilename())
                .typeFile(file.getContentType())
                .file(file)
                .length(String.valueOf(file.getSize()))
                .build();

        ResponseBucket responseOfImageSavedOnBucket = bucket.save(fileBodyDto);

        String urlCallback = linkCallback + "/" + responseOfImageSavedOnBucket.codeCallback();

        GenerateLinkFileResponse response = GenerateLinkFileResponse
                .builder()
                .codeCallback(responseOfImageSavedOnBucket.codeCallback())
                .linkCallback(urlCallback)
                .build();

        return response;
    }
}
