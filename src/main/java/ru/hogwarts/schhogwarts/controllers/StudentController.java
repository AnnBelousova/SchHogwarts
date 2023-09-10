package ru.hogwarts.schhogwarts.controllers;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.schhogwarts.models.Student;
import ru.hogwarts.schhogwarts.services.StudentService;

import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/student")
public class StudentController {
    final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @Operation(
            summary = "Creating new student",
            description = "User need to enter student name and age"
    )
    @PostMapping
    public Student createStudent(@RequestBody Student student) {
        return studentService.createStudent(student);
    }

    @Operation(
            summary = "Finding student by id",
            description = "User need to enter student id to find student"
    )

    @GetMapping("/{id}/by-id")
    public ResponseEntity findStudents(@PathVariable long id) {
        Optional<Student> student = studentService.findStudent(id);
        return ResponseEntity.ok(student);
    }

    @Operation(
            summary = "Updating student info by id",
            description = "User need to enter student id to update student info"
    )
    @PutMapping("/{id}")
    public Student updateStudent(@RequestBody Student student) {
        return studentService.updateStudent(student);
    }

    @Operation(
            summary = "Deleting student by id",
            description = "User need to enter student id to delete student"
    )
    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable long id) {
        studentService.deleteStudent(id);
    }

    @Operation(
            summary = "Getting students with age between two parameters",
            description = "The student list will be shown"
    )
    @GetMapping("/all")
    public Collection<Student> getStudentsList() {
        return studentService.studentsList();
    }
    @GetMapping
    public Collection<Student> getStudentsWithAgeBetween(@RequestParam int min, @RequestParam int max) {
        return studentService.getStudentsWithAgeBetween(min, max);
    }

    @GetMapping("/{id}")
    public String getFacultyByStudentId(@PathVariable long id) {
        return studentService.getFacultyByStudentId(id);
    }

    @GetMapping("/avg-age")
    public Integer getAVGStudentAge(){
        return studentService.getAVGStudentAge();
    }

    @GetMapping("/five")
    public Collection<Student> getStudentsLimitFiveDESC()  {
        return studentService.getStudentsLimitFiveDESC();
    }
    @GetMapping("starts-with")
    public String getStudentsStartWithCapitalADESC(){
        return studentService.getStudentsStartWithCapitalADESC();
    }

    @GetMapping("avg/age")
    public int getStudentAVGAgeByUsingStream(){
        return studentService.getStudentAVGAgeByUsingStream();
    }
}
