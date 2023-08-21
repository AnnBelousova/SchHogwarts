package ru.hogwarts.schhogwarts.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.hogwarts.schhogwarts.models.Student;

import java.util.Collection;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Collection<Student> findByAgeBetween(int min, int max);
    Student getById(Long studentId);

    @Query(value = "SELECT f.name FROM faculty f INNER JOIN student s ON s.faculty_id=f.id WHERE s.id=?1", nativeQuery = true)
    String getFacultyByStudentId(@Param("id") long id);



}
