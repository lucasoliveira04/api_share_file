package com.sharefiles.infra.controller;

import com.sharefiles.domain.dto.GenerateLinkFileResponse;
import com.sharefiles.domain.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/file")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @PostMapping(value = "/generate-link")
    public ResponseEntity<GenerateLinkFileResponse> generateLinkFile(@RequestBody MultipartFile file) {
        return ResponseEntity.ok(fileService.generateLinkFile(file));
    }
}
