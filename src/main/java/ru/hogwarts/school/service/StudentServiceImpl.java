package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.dto.FacultyDTO;
import ru.hogwarts.school.dto.StudentDTO;
import ru.hogwarts.school.mapper.StudentMapper;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;
import java.util.Optional;

import static ru.hogwarts.school.mapper.FacultyMapper.toFacultyDTO;
import static ru.hogwarts.school.mapper.StudentMapper.*;

@Service
public class StudentServiceImpl implements StudentService {
    private final StudentRepository repository;

    public StudentServiceImpl(StudentRepository repository) {
        this.repository = repository;
    }

    @Override
    public StudentDTO addStudent(StudentDTO studentDTO) {
        Student student = toStudent(studentDTO);
        repository.save(student);
        return studentDTO;
    }

    @Override
    public Collection<StudentDTO> getAllStudents() {
        Collection<Student> faculties = repository.findAll();
        return toCollectionStudentDTOs(faculties);
    }

    @Override
    public Collection<StudentDTO> findByAgeBetween(int from, int to) {
        Collection<Student> faculties = repository.findByAgeBetween(from, to);
        return toCollectionStudentDTOs(faculties);
    }

    @Override
    public Optional<StudentDTO> getStudentById(long id) {
        return repository.findById(id)
                .map(StudentMapper::toStudentDTO);
    }

    @Override
    public FacultyDTO getStudentFaculty(long id) {
        Optional<Student> student = repository.findById(id);
        if (student.isEmpty()) return null;
        Faculty faculty = student.get().getFaculty();
        return toFacultyDTO(faculty);
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
        repository.delete(student.get());
        return toStudentDTO(deletedStudent);
    }


}
