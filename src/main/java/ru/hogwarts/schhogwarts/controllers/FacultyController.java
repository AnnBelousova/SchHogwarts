package ru.hogwarts.schhogwarts.controllers;

import io.swagger.v3.oas.annotations.Operation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.schhogwarts.models.Faculty;
import ru.hogwarts.schhogwarts.services.FacultyService;

import java.util.Collection;
import java.util.Optional;


@RestController
@RequestMapping("/faculty")
public class FacultyController {
    final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @Operation(
            summary = "Creating new faculty",
            description = "User need to enter faculty name and color"
    )
    @PostMapping
    public Faculty createFaculty(@RequestBody Faculty faculty) {
        return facultyService.createFaculty(faculty);
    }

    @Operation(
            summary = "Finding faculty by id",
            description = "User need to enter student id to find faculty name"
    )
    @GetMapping("/{id}")
    public ResponseEntity findFaculty(@PathVariable long id) {
        Optional<Faculty> faculty = facultyService.findFaculty(id);
        return ResponseEntity.ok(faculty);
    }

    @Operation(
            summary = "Updating faculty info by id",
            description = "User need to enter faculty id to update faculty info"
    )
    @PutMapping("/{id}")
    public Faculty updateFaculty(@RequestBody Faculty faculty) {
        return facultyService.updateFaculty(faculty);
    }

    @Operation(
            summary = "Deleting faculty by id",
            description = "User need to enter faculty id to delete faculty"
    )
    @DeleteMapping("/{id}")
    public void deleteFaculty(@PathVariable long id) {
        facultyService.deleteFaculty(id);
    }

        @Operation(
            summary = "Getting faculty list",
            description = "The faculty list will be shown"
    )
    @GetMapping("/all")
    public Collection<Faculty> getFacultiesList() {
        return facultyService.facultiesList();
    }
    @GetMapping("/find")
    public Optional<Faculty> getFacultiesByNameOrColor(String name, String color) {
        return facultyService.getFacultyByNameOrColor(name, color);
    }



//    @Operation(
//            summary = "Getting student names",
//            description = "The student names list will be shown"
//    )
//    @GetMapping("/{faculty_id}")
//    public Collection<String> getStudentsByFacultyId(@PathVariable long faculty_id) {
//        return facultyService.getStudentsByFacultyId(faculty_id);
//    }
}
