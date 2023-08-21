package ru.hogwarts.schhogwarts.services;

import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.schhogwarts.models.Avatar;

import java.io.IOException;

public interface AvatarService {
    Avatar uploadAvatar(Long studentId, MultipartFile multipartFile) throws IOException;
    Avatar getAvatarById(Long id);

}
