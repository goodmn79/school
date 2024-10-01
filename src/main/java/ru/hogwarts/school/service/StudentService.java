package ru.hogwarts.school.service;

import ru.hogwarts.school.mogel.Student;

import java.util.Collection;
import java.util.Optional;

public interface StudentService {

    Student addStudent(Student student);

    Collection<Student> getAllStudents();

    Collection<Student> getAllStudentsWithAge(int age);

    Optional<Student> getStudentById(long id);

    Student changeStudentData(Student student);

    Optional<Student> deleteStudentById(long id);

}
