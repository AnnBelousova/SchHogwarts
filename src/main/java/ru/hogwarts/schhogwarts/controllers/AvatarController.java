package ru.hogwarts.schhogwarts.controllers;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.schhogwarts.models.Avatar;
import ru.hogwarts.schhogwarts.services.AvatarService;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

@RestController
public class AvatarController {

    private final AvatarService avatarService;

    public AvatarController(AvatarService avatarService) {
        this.avatarService = avatarService;
    }
    @PostMapping(value = "students/{studentId}/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Avatar uploadAvatar(@PathVariable Long studentId, @RequestParam MultipartFile multipartFile) throws IOException {
        return avatarService.uploadAvatar(studentId, multipartFile);
    }
    @GetMapping("/avatar/{avatarId}/avatar-from-db")
    public ResponseEntity<byte[]> downloadImage(Long avatarId){
        Avatar avatar = avatarService.getAvatarById(avatarId);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.parseMediaType(avatar.getMediaType()));
        httpHeaders.setContentLength(avatar.getData().length);
        return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body(avatar.getData());
    }
    @GetMapping("/avatar/{avatarId}/avatar-from-file")
    public void downloadAvatar(@PathVariable Long avatarId, HttpServletResponse httpServletResponse) throws IOException {
        Avatar avatar = avatarService.getAvatarById(avatarId);
        Path path = Path.of(avatar.getFilePath());
        try (
                InputStream is = Files.newInputStream(path);
                OutputStream os = httpServletResponse.getOutputStream();
        ){
            httpServletResponse.setStatus(200);
            httpServletResponse.setContentType(avatar.getMediaType());
            httpServletResponse.setContentLength(Math.toIntExact(avatar.getFileSize()));
            is.transferTo(os);
        }
    }
}
