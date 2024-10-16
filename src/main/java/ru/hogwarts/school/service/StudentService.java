package ru.hogwarts.school.service;

import ru.hogwarts.school.dto.FacultyDTO;
import ru.hogwarts.school.dto.StudentDTO;

import java.util.Collection;

public interface StudentService extends SchoolService<StudentDTO> {
    Collection<StudentDTO> findByAgeBetween(int from, int to);

    Collection<StudentDTO> findByAge(int age);

    FacultyDTO getFacultyOfStudent(long id);

    StudentDTO add(long facultyId, StudentDTO studentDTO);
}
