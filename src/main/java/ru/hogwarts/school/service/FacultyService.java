package ru.hogwarts.school.service;

import ru.hogwarts.school.mogel.Faculty;

import java.util.Collection;

public interface FacultyService {
    Faculty createFaculty(Faculty faculty);

    Faculty getFacultyById(long id);

    Faculty changeFaculty(Faculty faculty);

    Faculty deleteFacultyById(long id);

    Collection<Faculty> getAllFaculties();

    Collection<Faculty> getAllFacultiesWithColor(String color);
}
