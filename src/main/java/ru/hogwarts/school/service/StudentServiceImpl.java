package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.mogel.Student;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class StudentServiceImpl implements StudentService {
    private final Map<Long, Student> storage = new HashMap<>();
    private long idGenerator;

    @Override
    public Student addStudent(Student student) {
        student.setId(++idGenerator);
        storage.put(idGenerator, student);
        return student;
    }

    @Override
    public Student getStudentById(long id) {
        return storage.getOrDefault(id, null);
    }

    @Override
    public Student changeStudentData(Student student) {
        if (storage.containsKey(student.getId())) {
            storage.put(student.getId(), student);
            return student;
        }
        return null;
    }

    @Override
    public Student deleteStudentById(long id) {
        if (storage.containsKey(id)) {
            return storage.remove(id);
        }
        return null;
    }

    @Override
    public Collection<Student> getAllStudents() {
        return storage.values();
    }

    @Override
    public Collection<Student> getAllStudentsWithAge(int age) {
        return this.getAllStudents()
                .stream()
                .filter(s -> s.getAge() == age)
                .toList();
    }
}
