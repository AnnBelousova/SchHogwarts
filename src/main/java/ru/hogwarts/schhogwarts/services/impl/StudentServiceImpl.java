package ru.hogwarts.schhogwarts.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.schhogwarts.models.Student;
import ru.hogwarts.schhogwarts.repositories.StudentRepository;
import ru.hogwarts.schhogwarts.services.StudentService;

import java.util.Collection;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {

    Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);
    private StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Student createStudent(Student student) {
        logger.debug("The createStudent method starts.");
        return studentRepository.save(student);
    }

    @Override
    public void deleteStudent(long id) {
        logger.debug("The createStudent method starts.");
        studentRepository.deleteById(id);
    }

    @Override
    public Student updateStudent(Student student) {
        logger.debug("The updateStudent method starts.");
        return studentRepository.save(student);
    }

    @Override
    public Optional<Student> findStudent(long id) {
        logger.debug("The findStudent method starts.");
        return studentRepository.findById(id);
    }

    @Override
    public Collection<Student> studentsList() {
        logger.debug("The studentsList method starts.");
        return studentRepository.findAll();
    }

    @Override
    public Collection<Student> getStudentsWithAgeBetween(int min, int max) {
        logger.debug("The getStudentsWithAgeBetween method starts.");
        return studentRepository.findByAgeBetween(min, max);
    }

    @Override
    public String getFacultyByStudentId(long id) {
        logger.debug("The getFacultyByStudentId method starts.");
        return studentRepository.getFacultyByStudentId(id);
    }
    @Override
    public Collection<Student>  getStudentsLimitFiveDESC(){
        logger.debug("The getStudentsLimitFiveDESC method starts.");
        return studentRepository.getStudentsLimitFiveDESC();
    }
    @Override
    public Integer getAVGStudentAge(){
        logger.debug("The getAVGStudentAg method starts.");
        return studentRepository.getAVGStudentAge();
    }
}
