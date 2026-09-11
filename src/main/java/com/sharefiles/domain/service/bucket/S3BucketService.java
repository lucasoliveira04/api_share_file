package com.sharefiles.domain.service.bucket;

import com.sharefiles.db.entity.FileEntity;
import com.sharefiles.db.repository.FileRepository;
import com.sharefiles.domain.dto.bucket.ResponseBucket;
import com.sharefiles.domain.dto.bucket.SaveFileBucketResponse;
import com.sharefiles.domain.dto.file.FileBodyDto;
import com.sharefiles.domain.service.CallbackService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
public class S3BucketService implements IBucket{

    private static final String BUCKET_NAME = "files";
    private static final String PREFIX_KEY = "file/";
    private static final String TIMESTAMP = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
    private static final String UUID_KEY = UUID.randomUUID().toString();
    private final FileRepository fileRepository;
    private final CallbackService callbackService;

    @Override
    public ResponseBucket save(FileBodyDto file) throws IOException {
        try {
            String keyImage = buildKeyFile(PREFIX_KEY);

            PutObjectRequest saveResponse = PutObjectRequest
                    .builder()
                    .bucket(BUCKET_NAME)
                    .key(keyImage)
                    .contentType("application/octet-stream")
                    .build();

            log.info("m=file save with successfully, body response={}", saveResponse);

            var fileSaveEntity = FileEntity
                    .builder()
                    .fileName(file.fileName())
                    .lengthFile(file.length())
                    .typeFile(file.typeFile())
                    .codeUsedToCallback(callbackService.generateCode())
                    .createdAt(LocalDateTime.now())
                    .build();

            var response = ResponseBucket
                    .builder()
                    .message("Image saved with successfully")
                    .timestamp(fileSaveEntity.getCreatedAt().toString())
                    .build();

            fileRepository.save(fileSaveEntity);

            return response;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void getFile(String id) {

    }

    @Override
    public void delete(String id) {

    }

    private String buildKeyFile(String prefix) {
        return prefix + UUID_KEY + TIMESTAMP;
    }
}
