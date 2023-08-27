package ru.hogwarts.schhogwarts.services;

import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.schhogwarts.models.Avatar;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

public interface AvatarService {
    Avatar uploadAvatar(Long studentId, MultipartFile multipartFile) throws IOException;
    Avatar getAvatarById(Long id);
   List<Avatar> getAvatarsOnPage(Integer pageNumber, Integer pageSize);

}
