package ru.hogwarts.school.service;

import ru.hogwarts.school.dto.StudentDTO;

import java.util.Collection;
import java.util.Optional;

public interface StudentService {

    StudentDTO addStudent(StudentDTO studentDTO);

    Collection<StudentDTO> getAllStudents();

    Collection<StudentDTO> findStudentsByAge(int age);

    Optional<StudentDTO> getStudentById(long id);

    StudentDTO changeStudentData(StudentDTO studentDTO);

    StudentDTO deleteStudentById(long id);

}
