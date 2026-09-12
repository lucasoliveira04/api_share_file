package com.sharefiles.infra.controller;

import com.sharefiles.domain.dto.GenerateLinkFileResponse;
import com.sharefiles.domain.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/file")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @PostMapping(
                value = "/generate-link",
                consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<GenerateLinkFileResponse> generateLinkFile(@RequestParam("file") MultipartFile request) throws IOException {
        return ResponseEntity.ok(fileService.generateLinkFile(request));
    }
}
