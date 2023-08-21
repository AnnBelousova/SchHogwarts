package ru.hogwarts.schhogwarts.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.schhogwarts.models.Avatar;

import java.util.Optional;

public interface AvatarRepository extends JpaRepository<Avatar, Long> {
    Optional<Avatar> findByStudentId(Long studentId);
}
