package ru.hogwarts.schhogwarts.services;

import ru.hogwarts.schhogwarts.models.Faculty;

import java.util.Collection;
import java.util.Optional;

public interface FacultyService {
    Faculty createFaculty(Faculty faculty);

    void deleteFaculty(long id);

    Faculty updateFaculty(Faculty faculty);

    Optional<Faculty> findFaculty(long id);

    Collection<Faculty> facultiesList();

    Optional<Faculty> getFacultiesByNameOrColor(String name, String color);

    Collection<String> getStudentsByFacultyId(long faculty_id);

    String getFacultyNameWithLongerLength();

    int returnResult();
}
