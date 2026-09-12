package com.sharefiles.domain.service.bucket;

import com.sharefiles.db.entity.FileEntity;
import com.sharefiles.db.repository.FileRepository;
import com.sharefiles.domain.dto.bucket.ResponseBucket;
import com.sharefiles.domain.dto.file.FileBodyDto;
import com.sharefiles.domain.mapper.FileMapper;
import com.sharefiles.domain.service.CallbackService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
public class S3BucketService implements IBucket{

    private static final String BUCKET_NAME = "files";
    private static final String PREFIX_KEY = "file/";
    private final FileRepository fileRepository;
    private final CallbackService callbackService;
    private final FileMapper fileMapper;
    private final S3Client s3Client;

    @Override
    public ResponseBucket save(FileBodyDto file) throws IOException {
        try {
            String keyFile = buildKeyFile(PREFIX_KEY);

            PutObjectRequest saveResponse = PutObjectRequest
                    .builder()
                    .bucket(BUCKET_NAME)
                    .key(keyFile)
                    .contentType("application/octet-stream")
                    .build();

            s3Client.putObject(saveResponse, RequestBody.fromBytes(file.file().getBytes()));

            log.info("m=file save with successfully, body response={}", saveResponse);

            var fileSaveEntity = fileMapper.toEntity(file, callbackService.generateCode(), keyFile);

            var response = ResponseBucket
                    .builder()
                    .codeCallback(fileSaveEntity.getCodeUsedToCallback())
                    .timestamp(fileSaveEntity.getCreatedAt().toString())
                    .build();

            fileRepository.save(fileSaveEntity);

            return response;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public FileBodyDto getFile(String keyFile) {
        FileEntity fileEntity = findByKeyFile(keyFile);

        return FileBodyDto
                .builder()
                .fileName(fileEntity.getFileName())
                .length(fileEntity.getLengthFile())
                .typeFile(fileEntity.getTypeFile())
                .build();
    }

    @Override
    public void delete(String keyFile) {
        findByKeyFile(keyFile);
        deleteFile(keyFile);
    }

    private void deleteFile(String keyFile) {
        fileRepository.deleteByKeyFile(keyFile);
        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                .bucket(BUCKET_NAME)
                .key(keyFile)
                .build();

        s3Client.deleteObject(deleteObjectRequest);
    }

    private String buildKeyFile(String prefix) {
        return prefix + UUID.randomUUID() + "-" + LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
    }

    private FileEntity findByKeyFile(String keyFile) {
        Optional<FileEntity> fileEntity = fileRepository.findByKeyFile(keyFile);

        if (fileEntity.isEmpty()) {
            throw new RuntimeException("File not found");
        }

        return fileEntity.get();
    }
}
