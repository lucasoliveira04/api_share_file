package com.sharefiles.infra.controller;

import com.sharefiles.domain.service.callback.CallbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/files/callback")
@RequiredArgsConstructor
public class CallbackController {

    private final CallbackService callbackService;

    @GetMapping("/{code}")
    public ResponseEntity<byte[]> callback(@PathVariable String code) throws IOException {
        var file = callbackService.getFileByCode(code);


        return ResponseEntity
                .ok()
                .contentType(MediaType.parseMediaType(file.contentType()))
                .contentLength(file.content().length)
                .header(
                        "Content-Disposition",
                        "inline; filename=\"" + file.fileName() + "\""
                )
                .body(file.content());
    }

}
