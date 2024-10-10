package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.school.model.Student;

import java.util.Collection;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Collection<Student> findAllByFacultyId(long facultyId);

    Collection<Student> findByAgeBetween(int from, int to);

    Collection<Student> findByAge(int age);
}
