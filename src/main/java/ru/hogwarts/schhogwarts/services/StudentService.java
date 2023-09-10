package ru.hogwarts.schhogwarts.services;

import ru.hogwarts.schhogwarts.models.Student;

import java.util.Collection;
import java.util.Optional;

public interface StudentService {
    Student createStudent(Student student);

    void deleteStudent(long Id);

    Student updateStudent(Student student);

    Optional<Student> findStudent(long id);

    Collection<Student> studentsList();

    Collection<Student> getStudentsWithAgeBetween(int min, int max);

    String getFacultyByStudentId(long id);

    Collection<Student>  getStudentsLimitFiveDESC();

    Integer getAVGStudentAge();
    String getStudentsStartWithCapitalADESC();

    int getStudentAVGAgeByUsingStream();
}
