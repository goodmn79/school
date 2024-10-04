package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.dto.StudentDTO;
import ru.hogwarts.school.mapper.SchoolMapper;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;
import java.util.Optional;

import static ru.hogwarts.school.mapper.SchoolMapper.toStudentDTO;

@Service
public class StudentServiceImpl implements StudentService {
    private final StudentRepository repository;

    public StudentServiceImpl(StudentRepository repository) {
        this.repository = repository;
    }

    @Override
    public StudentDTO addStudent(StudentDTO studentDTO) {
        Student student = new Student(studentDTO.getId(), studentDTO.getName(), studentDTO.getAge());
        repository.save(student);
        return studentDTO;
    }

    @Override
    public Collection<StudentDTO> getAllStudents() {
        return repository.findAll()
                .stream()
                .map(SchoolMapper::toStudentDTO)
                .toList();
    }

    @Override
    public Collection<StudentDTO> findStudentsByAge(int age) {
        return repository.findStudentsByAge(age)
                .stream()
                .map(SchoolMapper::toStudentDTO)
                .toList();
    }

    @Override
    public Optional<StudentDTO> getStudentById(long id) {
        return repository.findById(id)
                .map(SchoolMapper::toStudentDTO);
    }

    @Override
    public StudentDTO changeStudentData(StudentDTO studentDTO) {
        Optional<Student> student = repository.findById(studentDTO.getId());
        if (student.isEmpty()) return null;
        student.map(s -> {
            s.setName(studentDTO.getName());
            s.setAge(studentDTO.getAge());
            return s;
        });
        Student changedStudent = student.get();
        repository.save(changedStudent);
        return toStudentDTO(changedStudent);
    }

    @Override
    public StudentDTO deleteStudentById(long id) {
        Optional<Student> student = repository.findById(id);
        if (student.isEmpty()) return null;
        Student deletedStudent = student.get();
        StudentDTO deletedStudentDTO = toStudentDTO(deletedStudent);
        repository.delete(deletedStudent);
        return deletedStudentDTO;
    }


}
