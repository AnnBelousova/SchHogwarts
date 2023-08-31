package ru.hogwarts.schhogwarts.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.hogwarts.schhogwarts.models.Faculty;

import java.util.Collection;
import java.util.Optional;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {
    Optional<Faculty> findFacultyByNameAndColor(String name, String faculty);

    @Query(value = "SELECT s.name FROM student s INNER JOIN faculty f ON s.faculty_id=f.id WHERE f.id=?1", nativeQuery = true)
    Collection<String> getStudentsByFacultyId(@Param("faculty_id") long faculty_id);
    @Override
    Optional<Faculty> findById(Long id);
    Optional<Faculty> deleteById(long id);
}
