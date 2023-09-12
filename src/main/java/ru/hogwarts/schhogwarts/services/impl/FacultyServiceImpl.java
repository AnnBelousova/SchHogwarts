package ru.hogwarts.schhogwarts.services.impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.schhogwarts.models.Faculty;
import ru.hogwarts.schhogwarts.repositories.FacultyRepository;
import ru.hogwarts.schhogwarts.services.FacultyService;

import java.util.*;
import java.util.stream.Stream;

@Service
public class FacultyServiceImpl implements FacultyService {
    Logger logger = LoggerFactory.getLogger(FacultyServiceImpl.class);
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
        logger.debug("The deleteFaculty method starts.");
        facultyRepository.deleteById(id);
    }

    @Override
    public Faculty updateFaculty(Faculty faculty) {
        logger.debug("The updateFaculty method starts.");
        return facultyRepository.save(faculty);
    }

    @Override
    public Optional<Faculty> findFaculty(long id) {
        logger.debug("The findFaculty method starts.");
        return facultyRepository.findById(id);
    }

    @Override
    public Collection<Faculty> facultiesList() {
        logger.debug("The facultiesList method starts.");
        return facultyRepository.findAll();
    }

    @Override
    public Optional<Faculty> getFacultiesByNameOrColor(String name, String color) {
        logger.debug("The getFacultiesByNameOrColor method starts.");
        return facultyRepository.findFacultyByNameAndColor(name, color);
    }

    @Override
    public Collection<String> getStudentsByFacultyId(long faculty_id) {
        logger.debug("The getStudentsByFacultyId method starts.");
        return facultyRepository.getStudentsByFacultyId(faculty_id);
    }
    @Override
    public String getFacultyNameWithLongerLength() {
        Map<String, Integer> facultyMap = new HashMap<>();
        for (Faculty f:facultyRepository.findAll()) {
            facultyMap.put(f.getName(),f.getName().length());
        }
        return   facultyMap.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .get().getKey().toString();
    }

    @Override
    public int returnResult(){
        long startTime = System.currentTimeMillis();
                int sum = Stream.iterate(1, a -> a + 1)
                .limit(1_000_000)
                  .parallel()
                       .reduce(0, (a, b) -> a + b );

        long endTime = System.currentTimeMillis();

        long timeElapsed = endTime - startTime;
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!" + timeElapsed);
        return sum;
    }
}
