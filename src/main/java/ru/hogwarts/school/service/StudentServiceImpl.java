package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.mogel.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {
    private final StudentRepository repository;

    public StudentServiceImpl(StudentRepository repository) {
        this.repository = repository;
    }

    @Override
    public Student addStudent(Student student) {
        return repository.save(student);
    }

    @Override
    public Collection<Student> getAllStudents() {
        return repository.findAll();
    }

    @Override
    public Collection<Student> getAllStudentsWithAge(int age) {
        return repository.findAllByAge(age);
    }

    @Override
    public Optional<Student> getStudentById(long id) {
        return repository.findById(id);
    }

    @Override
    public Student changeStudentData(Student student) {
        return repository.save(student);
    }

    @Override
    public Optional<Student> deleteStudentById(long id) {
        Optional<Student> deletedStudent = repository.findById(id);
        deletedStudent.ifPresent(repository::delete);
        return deletedStudent;
    }


}
