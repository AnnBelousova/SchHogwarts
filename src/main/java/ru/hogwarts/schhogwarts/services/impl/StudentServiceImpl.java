package ru.hogwarts.schhogwarts.services.impl;

import org.springframework.stereotype.Service;
import ru.hogwarts.schhogwarts.models.Student;
import ru.hogwarts.schhogwarts.repositories.StudentRepository;
import ru.hogwarts.schhogwarts.services.StudentService;

import java.util.Collection;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {


    private StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public void deleteStudent(long id) {
        studentRepository.deleteById(id);
    }

    @Override
    public Student updateStudent(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public Optional<Student> findStudent(long id) {
        return studentRepository.findById(id);
    }

    @Override
    public Collection<Student> studentsList() {
        return studentRepository.findAll();
    }

    @Override
    public Collection<Student> getStudentsWithAgeBetween(int min, int max) {
        return studentRepository.findByAgeBetween(min, max);
    }

    @Override
    public String getFacultyByStudentId(long id) {
        return studentRepository.getFacultyByStudentId(id);
    }

}
