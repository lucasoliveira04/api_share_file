package com.sharefiles.domain.bucket;

import com.sharefiles.db.entity.FileEntity;
import com.sharefiles.db.repository.FileRepository;
import com.sharefiles.domain.dto.bucket.ResponseBucket;
import com.sharefiles.domain.dto.file.FileBodyDto;
import com.sharefiles.domain.mapper.FileMapper;
import com.sharefiles.domain.service.CallbackService;
import com.sharefiles.domain.service.bucket.S3BucketService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class S3BucketServiceTest {

    @Mock
    private FileRepository fileRepository;

    @Mock
    private CallbackService callbackService;

    @Mock
    private FileMapper fileMapper;

    @Mock
    private S3Client s3Client;

    @Mock
    private MultipartFile multipartFile;

    @InjectMocks
    private S3BucketService s3BucketService;

    @Test
    void shouldSaveFileSuccessfully() throws IOException {

        when(multipartFile.getBytes()).thenReturn("file-test".getBytes());

        var file = FileBodyDto.builder()
                .fileName("teste.png")
                .length("100L")
                .typeFile("image/png")
                .file(multipartFile)
                .build();

        var entity = FileEntity.builder()
                .id(UUID.randomUUID())
                .fileName("teste.png")
                .lengthFile("100L")
                .typeFile("image/png")
                .keyFile("file/teste")
                .createdAt(LocalDateTime.now())
                .build();

        when(callbackService.generateCode()).thenReturn("ABC123");

        when(fileMapper.toEntity(eq(file), eq("ABC123"), anyString())).thenReturn(entity);

        when(fileRepository.save(entity)).thenReturn(entity);

        ResponseBucket response = s3BucketService.save(file);

        assertNotNull(response);
        assertEquals("Image saved with successfully", response.message());

        verify(callbackService).generateCode();

        verify(fileMapper).toEntity(eq(file), eq("ABC123"), anyString());

        verify(fileRepository).save(entity);

        verify(s3Client).putObject(any(PutObjectRequest.class), any(RequestBody.class));
    }
}