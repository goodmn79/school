package ru.hogwarts.school.service;

import ru.hogwarts.school.mogel.Faculty;

import java.util.Collection;
import java.util.Optional;

public interface FacultyService {

    Faculty addFaculty(Faculty faculty);

    Collection<Faculty> getAllFaculties();

    Collection<Faculty> getAllFacultiesWithColor(String color);

    Optional<Faculty> getFacultyById(long id);

    Faculty changeFaculty(Faculty faculty);

    Optional<Faculty> deleteFacultyById(long id);


}
