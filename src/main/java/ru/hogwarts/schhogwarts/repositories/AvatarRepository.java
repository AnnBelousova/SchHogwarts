package ru.hogwarts.schhogwarts.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.hogwarts.schhogwarts.models.Avatar;

import java.util.List;
import java.util.Optional;

public interface AvatarRepository extends JpaRepository<Avatar, Long> {
    Optional<Avatar> findByStudentId(Long studentId);
    List<Avatar> findAll();
}
