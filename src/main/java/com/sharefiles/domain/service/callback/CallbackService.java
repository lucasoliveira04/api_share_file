package com.sharefiles.domain.service.callback;

import com.sharefiles.db.entity.FileEntity;
import com.sharefiles.db.repository.FileRepository;
import com.sharefiles.domain.dto.bucket.FileResponseDto;
import com.sharefiles.domain.dto.file.FileBodyDto;
import com.sharefiles.domain.service.bucket.IBucket;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CallbackService {

    private final FileRepository fileRepository;
    private final IBucket bucketService;

    public FileResponseDto getFileByCode(String code) throws IOException {
        Optional<FileEntity> entity = fileRepository.findByCodeUsedToCallback(code);

        if (!entity.isPresent()) {
            throw new RuntimeException("File not found");
        }

        String keyFile = entity.get().getKeyFile();
        return bucketService.getFile(keyFile);
    }
}
