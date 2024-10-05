package ru.hogwarts.school.service;

import ru.hogwarts.school.dto.FacultyDTO;
import ru.hogwarts.school.dto.StudentDTO;

import java.util.Collection;
import java.util.Optional;

public interface FacultyService {

    FacultyDTO addFaculty(FacultyDTO facultyDTO);

    Collection<FacultyDTO> getAllFaculties();

    Collection<FacultyDTO> getAllFacultiesWithNameOrColor(String searchTerm);

    Optional<FacultyDTO> getFacultyById(long id);

    Collection<StudentDTO> getFacultyStudents(long id);

    FacultyDTO changeFaculty(FacultyDTO facultyDTO);

    FacultyDTO deleteFacultyById(long id);
}
