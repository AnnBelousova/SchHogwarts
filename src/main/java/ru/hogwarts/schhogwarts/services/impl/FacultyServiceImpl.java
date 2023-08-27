package ru.hogwarts.schhogwarts.services.impl;


import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.stereotype.Service;
import ru.hogwarts.schhogwarts.models.Faculty;
import ru.hogwarts.schhogwarts.models.Student;
import ru.hogwarts.schhogwarts.repositories.FacultyRepository;
import ru.hogwarts.schhogwarts.services.FacultyService;

import java.util.Collection;
import java.util.Optional;

@Service
public class FacultyServiceImpl implements FacultyService {
   final FacultyRepository facultyRepository;

    public FacultyServiceImpl(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    @Override
    public Faculty createFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }


    @Override
    public void deleteFaculty(long id) {
        facultyRepository.deleteById(id);
    }

    @Override
    public Faculty updateFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    @Override
    public Optional<Faculty> findFaculty(long id) {
        return facultyRepository.findById(id);
    }

    @Override
    public Collection<Faculty> facultiesList() {
        return facultyRepository.findAll();
    }

    @Override
    public Optional<Faculty> getFacultyByNameOrColor(String name, String color) {
        return facultyRepository.findFacultyByNameAndColor(name, color);
    }

    @Override
    public Collection<String> getStudentsByFacultyId(long faculty_id) {
        return facultyRepository.getStudentsByFacultyId(faculty_id);
    }
}
