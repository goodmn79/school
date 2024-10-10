package ru.hogwarts.school.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.service.AvatarService;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

@RestController
@RequestMapping("api/avatars")
public class AvatarController {
    private final AvatarService service;

    public AvatarController(AvatarService service) {
        this.service = service;
    }

    @PostMapping(value = "{studentId}/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadAvatar(@PathVariable long studentId, @RequestParam MultipartFile avatar) throws IOException {
        if (avatar.getSize() >= 1024 * 1024) return ResponseEntity.badRequest().body("file size is too large");
        service.upload(studentId, avatar);
        return ResponseEntity.ok().build();
    }

    @GetMapping("{studentId}/avatar-from-db")
    public ResponseEntity<byte[]> downloadAvatar(@PathVariable long studentId) {
        Avatar avatar = service.getById(studentId);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(avatar.getMediaType()));
        headers.setContentLength(avatar.getStudentAvatar().length);
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(avatar.getStudentAvatar());
    }

    @GetMapping("{studentId}/avatar")
    public void downloadAvatar(@PathVariable long studentId, HttpServletResponse response) throws IOException {
        Avatar avatar = service.getById(studentId);
        Path path = Path.of(avatar.getFilePath());
        try (InputStream inputStream = Files.newInputStream(path);
             OutputStream outputStream = response.getOutputStream()) {
            response.setContentType(avatar.getMediaType());
            response.setContentLength((int) avatar.getFileSize());
            inputStream.transferTo(outputStream);
        }
    }
}
