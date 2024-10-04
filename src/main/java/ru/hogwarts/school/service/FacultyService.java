package ru.hogwarts.school.service;

import ru.hogwarts.school.dto.FacultyDTO;

import java.util.Collection;
import java.util.Optional;

public interface FacultyService {

    FacultyDTO addFaculty(FacultyDTO facultyDTO);

    Collection<FacultyDTO> getAllFaculties();

    Collection<FacultyDTO> getAllFacultiesWithColor(String color);

    Optional<FacultyDTO> getFacultyById(long id);

    FacultyDTO changeFaculty(FacultyDTO facultyDTO);

    FacultyDTO deleteFacultyById(long id);


}
