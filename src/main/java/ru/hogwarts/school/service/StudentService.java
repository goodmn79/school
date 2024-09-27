package ru.hogwarts.school.service;

import ru.hogwarts.school.mogel.Student;

import java.util.Collection;

public interface StudentService {

    Student addStudent(Student student);

    Student getStudentById(long id);

    Student changeStudentData(Student student);

    Student deleteStudentById(long id);

    Collection<Student> getAllStudents();

    Collection<Student> getAllStudentsWithAge(int age);
}
